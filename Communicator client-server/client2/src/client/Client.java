package client;

import message.MessageLogger;
import controller.ClientScreenController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import static message.MessageType.*;
import message.MessageModel;

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
            MessageModel msg = new MessageModel(this.clientName, CONNECTING, String.format("Trying to connect to:\n---- HOST: %s  ---- PORT: %d...\n", address.getHostName(), address.getPort()));
            msgLogger.log(msg);

            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
            clientChannel.connect(address);

            return clientChannel;
        } catch (IOException e) {
            e.printStackTrace();
            MessageModel msg = new MessageModel(this.clientName, ERROR, "Cannot estabilish connection with server. \n");
            msgLogger.log(msg);
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
            MessageModel msg = new MessageModel(this.clientName, LOGOUT, " has been logged out. ");
            sendMessage(msg, clientChannel);
            this.connected = false;
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("Error while closing clientChannel");
        }
    }

    public boolean sendMessage(MessageModel message, SocketChannel clientChannel) {

        if (clientChannel == null) {
            MessageModel msg = new MessageModel(message.getUserName(), ERROR, "Error sending message - clientChannel is null.\n");
            msgLogger.log(msg);
            return false;
        }

        if (message == null) {
            MessageModel msg = new MessageModel(this.clientName, ERROR, "Error sending message - null message.\n");
            msgLogger.log(msg);
            return false;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.flip();

        try (ObjectOutputStream outputStream = new ObjectOutputStream(baos)) {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
            buffer = ByteBuffer.wrap(baos.toByteArray());
            clientChannel.write(buffer);
            outputStream.flush();
            baos.flush();
            clientChannel.register(selector, SelectionKey.OP_READ);

        } catch (Exception e) {
            e.printStackTrace();
            MessageModel msg = new MessageModel(message.getUserName(), ERROR, "Error while sending message. \n");
            msgLogger.log(msg);
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
                        MessageModel msg = new MessageModel("New user", CONNECTED, " has connected.\n");
                        sendMessage(msg, (SocketChannel) key.channel());
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
        MessageModel message = new MessageModel();
        InputStream bais;

        try {
            read = socketChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            key.cancel();
            socketChannel.close();
            return;
        }

        if (read == -1) {
            key.channel().close();
            key.cancel();
            return;
        }

        if (read > 0) {
            buffer.flip();
            try (ObjectInputStream ois = new ObjectInputStream(bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit()))) {
                message = (MessageModel) ois.readObject();
            } catch (ClassNotFoundException ex) {
                key.cancel();
                key.channel().close();
                socketChannel.close();
                return;
            }
        }

        if (message.getMessage().length() > 0 && message.getUserName().length() > 0 && message.getType() != 0) {
            switch (message.getType()) {
                case LOGIN: {
                    MessageModel msg = new MessageModel(message.getUserName(), ADDED, "dodano do tableView");
                    msgLogger.log(msg);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    MessageModel addedMsg = new MessageModel(this.clientName, ADDED, "dodano do tableView");
                    sendMessage(addedMsg, socketChannel);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    MessageModel msg2 = new MessageModel(message.getUserName(), LOGIN, String.format("%s", message.getMessage()));
                    msgLogger.log(msg2);
                    break;
                }
                case CONNECTED: {
                    MessageModel msg = new MessageModel(message.getUserName(), CONNECTED, message.getMessage());
                    msgLogger.log(msg);
                    break;
                }
                case REGISTER: {
                    MessageModel msg = new MessageModel(message.getUserName(), CONNECTED, message.getMessage());
                    break;
                }
                case LOGOUT: {
                    MessageModel msg = new MessageModel(message.getUserName(), REMOVED, "usunieto z tableView");
                    msgLogger.log(msg);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                    MessageModel removedMSG = new MessageModel(this.clientName, REMOVED, "usunieto z tableView");
                    sendMessage(removedMSG, socketChannel);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    MessageModel msg2 = new MessageModel(message.getUserName(), LOGOUT, String.format("%s", message.getMessage()));
                    msgLogger.log(msg2);
                    break;
                }
                case ADDED: {
                    MessageModel msg = new MessageModel(message.getUserName(), ADDED, message.getMessage());
                    msgLogger.log(msg);
                    break;
                }
                case REMOVED: {
                    MessageModel msg = new MessageModel(message.getUserName(), REMOVED, message.getMessage());
                    msgLogger.log(msg);
                    break;
                }
                case ERROR:{
                    MessageModel msg = new MessageModel(message.getUserName(), ERROR, "usunieto z tableView");
                    msgLogger.log(msg);
                    break;
                }
                default:
                    MessageModel msg = new MessageModel(message.getUserName(), MESSAGE, String.format("%s\n", message.getMessage()));
                    msgLogger.log(msg);
                    break;
            }
        }
    }
}
