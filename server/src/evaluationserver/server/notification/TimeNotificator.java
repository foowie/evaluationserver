package evaluationserver.server.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeNotificator extends Thread implements Notificator {
	private static final Logger logger = Logger.getLogger(TimeNotificator.class.getPackage().getName());	
	
	private final long time;
	private List<Notifiable> notifiable = new ArrayList<Notifiable>();

	public TimeNotificator(long time) {
		this.time = time;
		setDaemon(true);
	}
	
	public TimeNotificator(long time, Notifiable notifiable) {
		this(time);
		addNotifiable(notifiable);
	}

	public long getTime() {
		return time;
	}

	@Override
	public void runNotificator() {
		start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException ex) {
			}
			notifyObjects();
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
