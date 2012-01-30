package controllers;

import models.Role;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With(Secure.class)
public class Admins extends CRUD {
	
}
