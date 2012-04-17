package evaluationserver.server.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificatorList extends ArrayList {
	public NotificatorList(Collection<Notificator> collection) {
		super(collection);
	}
	
	public void addNotifiable(Notifiable notifiable) {
		for(Notificator n : getNotificators())
			n.addNotifiable(notifiable);
	}
	
	public void removeNotifiable(Notifiable notifiable) {
		for(Notificator n : getNotificators())
			n.removeNotifiable(notifiable);
	}
	
	public void runNotificators() {
		for(Notificator n : getNotificators())
			n.runNotificator();
	}
	
	public List<Notificator> getNotificators() {
		return this;
	}
	
}
