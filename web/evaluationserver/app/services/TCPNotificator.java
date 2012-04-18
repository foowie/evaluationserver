package services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class notify evaluation server to evaluate solutions
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class TCPNotificator {

	private int port;
	private InetAddress addr;

	public TCPNotificator(InetAddress addr, int port) {
		this.port = port;
		this.addr = addr;
	}

	public void sendNotification() {
		try {
			Socket socket = new Socket(addr, port);
			socket.close();
		} catch (java.net.ConnectException ex) {
			Logger.getLogger(TCPNotificator.class.getName()).log(Level.WARNING, ex.getMessage());
		} catch (IOException ex) {
			Logger.getLogger(TCPNotificator.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
