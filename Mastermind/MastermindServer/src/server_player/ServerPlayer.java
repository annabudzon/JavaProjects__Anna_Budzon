package server_player;

import controller.GameController;
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
import message_game.MessageColors;
import message_game.MessageGuessLogger;
import static message_game.MessageState.*;

public class ServerPlayer implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private InetSocketAddress address;

    private ServerSocketChannel serverChannel;

    private Selector selector;

    private final MessageGuessLogger msgLogger;

    private boolean connected;

    private boolean connectedClient;

    public ServerPlayer(String host, int port, GameController controller) throws IOException {
        this.address = new InetSocketAddress(host, port);
        this.selector = initSelector();
        this.msgLogger = new MessageGuessLogger(controller);
        connected = true;
        connectedClient = false;
    }

    public ServerSocketChannel getSocket() {
        return serverChannel;
    }

    @Override
    public void run() {
        while (connected) {
            start();
        }
    }

    public void start() {
        try {
            logger(String.format("GAME: Listening for connections on \n---- HOST: %s  ---- PORT: %d...", address.getHostName(), address.getPort()));

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
                        MessageColors connection = new MessageColors(CONNECTED);
                        msgLogger.log(connection);
                        connectedClient = true;
                    } else if (key.isReadable()) {
                        read(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger("GAME: Error occured during server start. \n");
            System.exit(0);
        }
    }

    public void stop() {
        try {
            connectedClient = false;
            connected = false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Thread sleep exception.");
            }
            serverChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
            logger("GAME: Error occured during server stop. \n");
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
        if (!connectedClient) {
            logger("GAME: Accepting connection... \n");

            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void read(SelectionKey key) throws IOException {
        logger("GAME: Reading data from client");

        SocketChannel clientChannel = (SocketChannel) key.channel();

        MessageColors message = new MessageColors();
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
                message = (MessageColors) ois.readObject();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                key.cancel();
                key.channel().close();
                clientChannel.close();
                return;
            }
        } else {
            logger("GAME: Nothing has been read. \n");
        }

        if (message.getState() != 0 && message.getColor1() != null && message.getColor2() != null && message.getColor3() != null && message.getColor4() != null) {
            msgLogger.log(message);
        } else {
            logger("GAME: Fields: colors or state are empty.\n");
        }
    }

    public boolean sendMessage(MessageColors msg) throws IOException {
        logger("GAME: Sending answer...");

        if (msg.getColor1() == null
                || msg.getColor2() == null
                || msg.getColor3() == null
                || msg.getColor4() == null) {
            logger("Error sending message - null message.\n");
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
                    logger("GAME: Error while sending the message.\n");
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
