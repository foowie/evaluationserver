package services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TCPNotificator {

	private int port;
	private InetAddress addr;

	public TCPNotificator(InetAddress addr, int port) {
		this.port = port;
		this.addr = addr;
	}

	public void sendNotification() throws IOException {
		Socket socket = new Socket(addr, port);
		socket.close();
	}
}
