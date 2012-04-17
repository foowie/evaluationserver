package controllers;

import play.mvc.*;

/**
 * Application entrance
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class Application extends Controller {

	public static void index() throws Throwable {
		if (Security.getUsername() != null) {
			Security.onAuthenticated();
		}
		// redirect to login
		Secure.login();
	}
}