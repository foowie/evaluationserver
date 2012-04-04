package evaluationserver.server.execution;

import evaluationserver.server.notification.Notificator;
import evaluationserver.server.notification.NotificatorList;
import java.util.Collection;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Run {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new FileSystemXmlApplicationContext("config/configuration.xml");
		BeanFactory factory = context;

		// add notificators
		Collection<Notificator> nl = factory.getBean(NotificatorList.class);
		for (Notificator n : nl) {
			n.runNotificator();
		}

		// get server instance
		Server server = factory.getBean(Server.class);

		// run server
		server.runServer();

	}
}
