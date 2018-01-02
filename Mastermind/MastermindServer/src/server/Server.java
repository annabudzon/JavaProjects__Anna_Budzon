package server;

import controller.MainScreenController;
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
import message_communicator.MessageLogger;
import message_communicator.MessageModel;
import static message_communicator.MessageType.*;

public class Server implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private InetSocketAddress address;

    private ServerSocketChannel serverChannel;

    private Selector selector;

    private String serverName;

    private final MessageLogger msgLogger;

    private boolean connected;

    private boolean ifClientConnected;

    private MainScreenController controller;    


    public Server(String host, int port, MainScreenController controller, String serverName) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
        this.serverName = serverName;
        this.controller = controller;
        this.msgLogger = new MessageLogger(this.controller);
        connected = true;
        ifClientConnected = false;
    }

    public ServerSocketChannel getSocket() {
        return serverChannel;
    }
    
    public boolean isClientConnected(){
        return ifClientConnected;
    }

    public void start() {
        try {
            MessageModel msg = new MessageModel(this.serverName, LISTENING, String.format("Listening for connections on \n---- HOST: %s  ---- PORT: %d...", address.getHostName(), address.getPort()));
            msgLogger.log(msg);

            while (connected) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    } else if (key.isAcceptable()) {
                        accept(key);
                        MessageModel connection = new MessageModel(this.serverName, CONNECTED, " connected. \n");
                        msgLogger.log(connection);
                        ifClientConnected = true;
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            MessageModel msg = new MessageModel(this.serverName, ERROR, "Error occured during server start. \n");
            msgLogger.log(msg);
            System.exit(0);
        }
    }

    public void stop() {
        try {
            connected = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread sleep exception");
            }
            serverChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            MessageModel msg = new MessageModel(this.serverName, ERROR, "Error occured during server stop. \n");
            msgLogger.log(msg);
        }
    }

    @Override
    public void run() {
        while (connected) {
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
        if (!ifClientConnected) {
            MessageModel msg = new MessageModel(this.serverName, ACCEPTING, "Accepting connection... \n");
            msgLogger.log(msg);

            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void read(SelectionKey key) throws IOException {
        logger("Reading data from client");

        SocketChannel clientChannel = (SocketChannel) key.channel();
        MessageModel message = null;
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
            MessageModel msg = new MessageModel(this.serverName, ERROR, "Nothing had been read.\n");
            msgLogger.log(msg);
        }

        if (message.getMessage().length() > 0 && message.getUserName().length() > 0 && message.getType() != 0 && !message.getMessage().equals("")) {

            if (message.getType() == LOGOUT) {
                this.ifClientConnected = false;
            }
            msgLogger.log(message);

        } else {
            MessageModel msg = new MessageModel(this.serverName, ERROR, "Fields: message, userName or type is empty.\n");
            msgLogger.log(msg);
        }
    }

    public boolean sendMessage(MessageModel msg) throws IOException {
        logger("Sending message: " + msg.getMessage());

        if (msg.getMessage() == null) {
            MessageModel nullMsg = new MessageModel(this.serverName, ERROR, "Error sending message - null message.\n");
            msgLogger.log(nullMsg);
            return false;
        }

        ByteArrayOutputStream baos;
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
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
                    MessageModel messageFailed = new MessageModel(this.serverName, ERROR, "Error while sending the message: " + msg.getMessage() + "\n");
                    msgLogger.log(messageFailed);

                    return false;
                }
            }
        }
        return true;
    }

    public static void logger(String msg) {
        System.out.println(msg);
    }
}
