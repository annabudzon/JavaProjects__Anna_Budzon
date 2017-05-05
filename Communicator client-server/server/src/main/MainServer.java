package main;

import server.Server;
import java.io.IOException;

public class MainServer {

    private static final String HOST = "127.0.0.1";

	private static final int PORT = 9999;

	public static void main(String[] args) throws IOException {
		new Thread(new Server(HOST, PORT)).start();
	}
}
