package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import static message.MessageType.*;

public class Server implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private InetSocketAddress address;

    private ServerSocketChannel serverChannel;

    private Selector selector;

    public Server(String host, int port) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
    }

    public void start() {
        try {
            logger(String.format("Listening for connections on \n---- HOST: %s  ---- PORT: %d...", address.getHostName(), address.getPort()));

            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    } else if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger("Error occured during server start.");
            System.exit(0);
        }
    }

    public void stop() {
        try {
            serverChannel.close();
        } catch (IOException e) {
            logger("Error occured during server stop.");
        }
    }

    @Override
    public void run() {
        while (true) {
            start();
        }

    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();

        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(address);
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }

    private void accept(SelectionKey key) throws IOException {
        logger("Accepting connection");

        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        logger("Reading data from client");

        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
        int read = 0;

        try {
            read = clientChannel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            clientChannel.close();
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
            logger(String.format("%s\n", data));
            String message = defineTypeMessage(data);
            broadcast(message, key);
        }
    }

    private void broadcast(String msg, SelectionKey k) throws IOException {
        ByteBuffer msgBuf = ByteBuffer.wrap(msg.getBytes());
        logger("Broadcasting message to all clients: " + msg);
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel && key != k) {
                SocketChannel sch = (SocketChannel) key.channel();
                sch.register(selector, SelectionKey.OP_WRITE);
                sch.write(msgBuf);
                msgBuf.rewind();
                sch.register(selector, SelectionKey.OP_READ);
            }
        }
    }

    public String defineTypeMessage(String msg) {
        String[] parts = msg.split(":");
        int type = Integer.parseInt(parts[0]);
        String name = parts[1].replaceAll(" ", "");

        switch (type) {
            case REGISTER:
                msg = "REGISTERED: " + parts[1] + "\n";
                break;
            case CONNECTED:
                msg = "CONNECTED: " + parts[1] + "\n";
                break;
            case LOGIN:
                msg = "LOGIN: " + parts[1] + "\n";
                break;
            case LOGOUT:
                msg = "LOGOUT: " + parts[1] + "\n";
                break;
            case MESSAGE:
                msg = parts[1] + ": " + parts[2] + "\n";
                break;
            case ERROR:
                msg = "ERROR: " + parts[1] + "\n";
                break;
            case ADDED:
                msg = "ADDED:" + name;// + "\n";
                break;
            case REMOVED:
                msg = "REMOVED:" + name;// + "\n";
                break;
            default:
                System.out.println("Unvalid type.\n");
                break;
        }
        return msg;
    }

    public static void logger(String msg) {
        System.out.println(msg);
    }
}
