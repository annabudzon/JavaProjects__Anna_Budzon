package server;

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
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import message.MessageModel;

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
            e.printStackTrace();
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

        MessageModel message = new MessageModel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
        int read = 0;

        try {
            read = clientChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            key.cancel();
            clientChannel.close();
            return;
        }

        if (read == -1) {
            key.channel().close();
            key.cancel();
            return;
        }

        if (read > 0) {

            InputStream bais;
            buffer.flip();
            try (ObjectInputStream ois = new ObjectInputStream(bais = new ByteArrayInputStream(buffer.array(), 0, buffer.limit()))) {
                message = (MessageModel) ois.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                key.cancel();
                key.channel().close();
                clientChannel.close();
                return;
            }
        } else {
            logger("Nothing has been read. \n");
        }

        if (message.getMessage().length() > 0 && message.getUserName().length() > 0 && message.getType() != 0) {
            logger(String.format("%s\n", message.toString()));
            broadcast(message, key);
        } else {
            logger("Fields: message, userName and type are empty.\n");
        }
    }

    private void broadcast(MessageModel msg, SelectionKey k) throws IOException {
        logger("Broadcasting message to all clients: " + msg.getMessage());

        ByteArrayOutputStream baos;

        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel && key != k) {
                SocketChannel sch = (SocketChannel) key.channel();
                sch.register(selector, SelectionKey.OP_WRITE);
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.flip();

                try (ObjectOutputStream outputStream = new ObjectOutputStream(baos = new ByteArrayOutputStream())) {
                    outputStream.reset();
                    outputStream.writeObject(msg);
                    outputStream.flush();
                    buffer = ByteBuffer.wrap(baos.toByteArray());
                    sch.write(buffer);
                    outputStream.flush();
                    baos.flush();
                    sch.register(selector, SelectionKey.OP_READ);

                } catch (IOException e) {
                    e.printStackTrace();
                    logger("Error while sending the message: " + msg.getMessage());
                    return;
                }
            }
        }
    }

    public static void logger(String msg) {
        System.out.println(msg);
    }
}
