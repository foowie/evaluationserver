package controllers;

import models.Role;
import models.User;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Change users / admins password
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN_OR_CONTESTANT)
@With(Secure.class)
public class ChangePassword extends Controller {

	public static void index() {
		render();
	}

	public static void submit(@Required String oldPassword, @Required String newPassword, @Required String newPassword2) {
		if (params._contains("cancel")) {
			Security.onAuthenticated();
		}

		oldPassword = Security.hashPassword(oldPassword);
		User user = User.getLoggedUser();

		validation.equals(newPassword, newPassword2).message("New passwords are different !");
		validation.equals(oldPassword, user.password).message("Invalid old password !");
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index();
		}

		user.password = newPassword;
		user.save();

		Security.onAuthenticated();
	}
}
