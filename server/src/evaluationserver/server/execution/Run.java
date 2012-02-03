package evaluationserver.server.execution;

import evaluationserver.server.notification.Notificator;
import evaluationserver.server.notification.NotificatorList;
import java.util.Collection;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class Run {
	public static void main(String[] args) throws Exception {

		Resource resource = new FileSystemResource("configuration.xml");
		BeanFactory factory = new XmlBeanFactory(resource);
		
		// add notificators
		Collection<Notificator> nl = factory.getBean(NotificatorList.class);
		for(Notificator n : nl) {
			n.runNotificator();
		}		
		
		// get server instance
		Server server = factory.getBean(Server.class);
		
		// run server
		server.runServer();
		
	}
}
