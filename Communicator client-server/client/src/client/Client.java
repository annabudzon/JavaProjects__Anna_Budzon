package client;

import message.MessageLogger;
import controller.ClientScreenController;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import static message.MessageType.*;

public class Client implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private final InetSocketAddress address;

    private final Selector selector;

    private SocketChannel clientChannel;

    private String clientName;

    private final ClientScreenController controller;

    private final MessageLogger msgLogger;

    private boolean connected;

    public Client(String host, int port, ClientScreenController controller, String clientName) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
        this.controller = controller;
        this.clientName = clientName;
        this.msgLogger = new MessageLogger(controller);
        connected = true;
    }

    public void setUserName(String name) {
        this.clientName = name;
    }

    public SocketChannel connect() {
        try {
            connected = true;
            msgLogger.log(CONNECTING, String.format("Trying to connect to:\n---- HOST: %s  ---- PORT: %d...\n", address.getHostName(), address.getPort()));

            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
            clientChannel.connect(address);

            return clientChannel;
        } catch (IOException e) {
            msgLogger.log(ERROR, "Cannot estabilish connection with server. \n");
            return null;
        }
    }

    public SocketChannel getSocket() {
        return clientChannel;
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_WRITE);

    }

    public void disconnect(SocketChannel clientChannel) {
        try {
            sendMessage(LOGOUT + ": " + this.clientName + "\n", clientChannel);
            this.connected = false;
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("Error while closing clientChannel");
        }
    }

    public boolean sendMessage(String message, SocketChannel clientChannel) {
        if (clientChannel == null) {
            msgLogger.log(ERROR, "Error sending message - clientChannel is null.\n");
            return false;
        }

        if (message == null) {
            msgLogger.log(ERROR, "Error sending message - null message.\n");
            return false;
        }

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.put(message.getBytes());
        buffer.flip();

        try {
            clientChannel.write(buffer);
            clientChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            msgLogger.log(ERROR, "Error while sending message");
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        connect();

        while (connected) {
            try {
                selector.select();

                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    } else if (key.isConnectable()) {
                        connect(key);
                        sendMessage(CONNECTED + ": New user has connected.\n", (SocketChannel) key.channel());
                    } else if (key.isReadable()) {
                        Thread.sleep(2000);
                        read(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Selector initSelector() throws IOException {
        return Selector.open();
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();

        int read = 0;
        try {
            read = socketChannel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (read == -1) {
            key.channel().close();
            key.cancel();
            return;
        }
        buffer.flip();
        String data = new String(buffer.array()).trim();
        if (data.length() > 0) {
            if (data.contains(":")) {
                String[] parts = data.split(":");
                switch (parts[0]) {
                    case "LOGIN":
                        {
                            String name = parts[1].replaceAll(" ", "");
                            msgLogger.log(ADDED, name);
                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                            sendMessage(ADDED + ":" + this.clientName, socketChannel);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            msgLogger.log(MESSAGE, String.format("%s\n", data));
                            break;
                        }
                    case "LOGOUT":
                        {
                            String name = parts[1].replaceAll(" ", "");
                            msgLogger.log(REMOVED, name);
                            msgLogger.log(MESSAGE, String.format("%s\n", data));
                            break;
                        }
                    case "ADDED":
                        {
                            String name = parts[1].replaceAll(" ", "");
                            msgLogger.log(ADDED, name);
                            break;
                        }
                    case "REMOVED":
                        {
                            String name = parts[1].replaceAll(" ", "");
                            msgLogger.log(REMOVED, name);
                            break;
                        }
                    default:
                        msgLogger.log(MESSAGE, String.format("%s\n", data));
                        break;
                }
            } else {
                msgLogger.log(MESSAGE, String.format("%s\n", data));
            }
        }
    }
}
