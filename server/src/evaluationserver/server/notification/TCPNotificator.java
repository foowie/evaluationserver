package evaluationserver.server.notification;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPNotificator extends Thread implements Notificator {
	private static final Logger logger = Logger.getLogger(TCPNotificator.class.getPackage().getName());	

	private final int port;
	private List<Notifiable> notifiable = new ArrayList<Notifiable>();

	public TCPNotificator(int port) {
		this.port = port;
		setDaemon(true);
	}
	
	public TCPNotificator(int port, Notifiable notifiable) {
		this(port);
		addNotifiable(notifiable);
	}

	public int getPort() {
		return port;
	}

	@Override
	public void runNotificator() {
		start();
	}

	@Override
	public void run() {
		final ServerSocket ss;
		try {
			ss = new ServerSocket(port);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
			return;
		}

		while (true) {
			Socket socket = null;
			try {
				socket = ss.accept();
				notifyObjects();
			} catch (IOException ex) {
				logger.log(Level.SEVERE, null, ex);
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException ex) {
					}
				}
			}
		}
	}

	protected void notifyObjects() {
		logger.log(Level.FINER, "Notification !");
		for (Notifiable n : this.notifiable) {
			n.notification();
		}
	}

	@Override
	public final void addNotifiable(Notifiable notifiable) {
		this.notifiable.add(notifiable);
	}

	@Override
	public void removeNotifiable(Notifiable notifiable) {
		this.notifiable.remove(notifiable);
	}
}
