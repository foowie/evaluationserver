package controllers;

import models.User;
import play.mvc.Before;

public class Tasks extends CRUD {
	
	@Before(only="create")
	public static void beforeCreate() {
		params.put("object.creator.id", User.getLoggedUser().getId().toString());
	}
	
}
