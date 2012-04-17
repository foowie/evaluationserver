package evaluationserver.server.execution;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Run {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new FileSystemXmlApplicationContext("config/configuration.xml");
		BeanFactory factory = context;

		// get server instance
		Server server = factory.getBean(Server.class);

		// run server
		server.runServer();

	}
}
