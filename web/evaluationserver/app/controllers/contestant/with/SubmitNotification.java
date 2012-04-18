package controllers.contestant.with;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import play.Play;
import play.mvc.After;
import play.mvc.Controller;
import services.TCPNotificator;

/**
 * Helper that notify server after action
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class SubmitNotification extends Controller {

	/**
	 * Notify evaluation server after actions submit and unevaluate
	 * @throws UnknownHostException
	 * @throws IOException 
	 */
	@After
	public static void checkCompetition() throws UnknownHostException, IOException {
		String action = request.action.substring(request.action.lastIndexOf(".") + 1);
		if (!action.equals("submit") && !action.equals("unevaluate")) {
			return;
		}

		InetAddress addr = InetAddress.getByName(Play.configuration.getProperty("server.host", "localhost"));
		int port = Integer.parseInt(Play.configuration.getProperty("server.notification.port", "5879"));
		TCPNotificator notificator = new TCPNotificator(addr, port);
		notificator.sendNotification();
	}
}
