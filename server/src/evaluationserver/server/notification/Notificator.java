package evaluationserver.server.notification;


public interface Notificator {
	public void addNotifiable(Notifiable notifiable);
	public void removeNotifiable(Notifiable notifiable);
	public void runNotificator();
}
