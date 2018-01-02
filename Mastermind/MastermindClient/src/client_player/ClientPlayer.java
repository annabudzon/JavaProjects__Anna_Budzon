package client_player;

import controller.GameController;
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
import message_game.MessageColors;
import message_game.MessageGuessLogger;
import static message_game.MessageState.CONNECTED;

public class ClientPlayer implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private final InetSocketAddress address;

    private final Selector selector;

    private SocketChannel clientChannel;

    private final MessageGuessLogger msgLogger;

    private boolean connected;

    public ClientPlayer(String host, int port, GameController controller) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
        this.msgLogger = new MessageGuessLogger(controller);
        connected = false;
    }

    public SocketChannel connect() {
        try {
            logger(String.format("GAME: Trying to connect to:\n---- HOST: %s  ---- PORT: %d...\n", address.getHostName(), address.getPort()));

            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_CONNECT);
            clientChannel.connect(address);
            connected = true;
            return clientChannel;
        } catch (IOException e) {
            e.printStackTrace();
            logger("GAME:Cannot estabilish connection with server. \n");
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
            this.connected = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread sleep.");
            }
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("GAME: Error while closing clientChannel");
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
                        MessageColors msg = new MessageColors(CONNECTED);
                        msgLogger.log(msg);
                        sendMessage(msg, (SocketChannel) key.channel());
                    } else if (key.isReadable()) {
                        Thread.sleep(2000);
                        read(key);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("GAME: Interrupted Exception.\n");
            } catch (IOException e) {
                System.out.println("GAME: IOException.\n");
            }
        }
    }

    private Selector initSelector() throws IOException {
        return Selector.open();
    }

    private void read(SelectionKey key) throws IOException {

        logger("GAME: Reading from server.\n");

        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.clear();
        int read = 0;
        MessageColors message = new MessageColors();
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
                message = (MessageColors) ois.readObject();
            } catch (ClassNotFoundException ex) {
                key.cancel();
                key.channel().close();
                socketChannel.close();
                return;
            }
        }

        if (message.getState() != 0 && message.getColor1() != null && message.getColor2() != null && message.getColor3() != null && message.getColor4() != null) {
            msgLogger.log(message);
        } else {
            logger("GAME: Fields: colors and state are empty. \n");
        }
    }

    public boolean sendMessage(MessageColors message, SocketChannel clientChannel) {
        logger("GAME: Sending message.\n");

        try {
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException ex) {
            ex.printStackTrace();
            System.out.println("ClosedChannelException. \n");
        }
        if (clientChannel == null) {
            logger("GAME: Error sending message - clientChannel is null.\n");
            return false;
        }

        if (message == null) {
            logger("GAME: Error sending message - null message.\n");
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
            logger("GAME: Error while sending message. \n");
            return false;
        }
        return true;
    }

    public static void logger(String msg) {
        System.out.println(msg);
    }
}
