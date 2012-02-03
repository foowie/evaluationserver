package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
@CRUD.For(models.Category.class)
public class Categories extends CRUD {
	
}
