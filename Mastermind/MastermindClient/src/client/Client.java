package client;

import controller.MainScreenController;
import message_communicator.MessageLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import static message_communicator.MessageType.*;
import message_communicator.MessageModel;

public class Client implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private final InetSocketAddress address;

    private final Selector selector;

    private SocketChannel clientChannel;

    private String clientName;

    private final MessageLogger msgLogger;

    private boolean connected;
    
    private boolean ifServerConnected;

    public Client(String host, int port, MainScreenController controller, String clientName) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
        this.clientName = clientName;
        this.msgLogger = new MessageLogger(controller);
        connected = false;
        ifServerConnected = false;
    }
    
    

    public void setUserName(String name) {
        this.clientName = name;
    }

    public SocketChannel connect() {
        try {
            MessageModel msg = new MessageModel(this.clientName, CONNECTING, String.format("Trying to connect to:\n---- HOST: %s  ---- PORT: %d...\n", address.getHostName(), address.getPort()));
            msgLogger.log(msg);

            this.clientChannel = SocketChannel.open();
            this.clientChannel.configureBlocking(false);
            this.clientChannel.register(selector, SelectionKey.OP_CONNECT);
            this.clientChannel.connect(address);
            connected = true;
            return clientChannel;
        } catch (IOException e) {
            e.printStackTrace();
            MessageModel msg = new MessageModel(this.clientName, ERROR, "Cannot estabilish connection with server. \n");
            msgLogger.log(msg);
            return null;
        }
    }
    
    public boolean isServerConnected(){
        return ifServerConnected;
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
        ifServerConnected = true;

    }

    public void disconnect(SocketChannel clientChannel) {
        try {
            this.connected = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread sleep.");
            }
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("Error while closing clientChannel");
        }
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
                        MessageModel msg = new MessageModel(this.clientName, CONNECTED, " have join you. Lets play a game! \n Wait for his guess...\n");
                        sendMessage(msg, (SocketChannel) key.channel());
                        MessageModel connectMsg = new MessageModel("You ", CONNECTED, "have been connected. Lets play! \n");
                        msgLogger.log(connectMsg);
                    } else if (key.isReadable()) {
                        Thread.sleep(2000);
                        read(key);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Interrupted Exception.\n");
            } catch (IOException e) {
                System.out.println("IOException.\n");
            }
        }
    }

    private Selector initSelector() throws IOException {
        return Selector.open();
    }

    private void read(SelectionKey key) throws IOException {
        System.out.println("Reading message....");

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
                ex.printStackTrace();
                key.cancel();
                key.channel().close();
                socketChannel.close();
                return;
            }
        } else {
            MessageModel msg = new MessageModel(this.clientName, ERROR, "Nothing has been read.\n");
            msgLogger.log(msg);
        }
        if (message.getMessage().length() > 0 && message.getUserName().length() > 0 && message.getType() != 0 && !message.getMessage().equals("")) {
            msgLogger.log(message);
        } else {
            MessageModel msg = new MessageModel(this.clientName, ERROR, "Fields: message, userName or type is empty.\n");
            msgLogger.log(msg);
        }
    }

    public boolean sendMessage(MessageModel message, SocketChannel clientChannel) {
        System.out.println("Sending message....");

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

        try {
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException ex) {
            ex.printStackTrace();
            System.out.println("ClosedChannelException. \n");
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
}
