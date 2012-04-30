package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import models.Role;
import play.mvc.With;

/**
 * CRUD controller for model Category
 * REQ-2.1, REQ-2.2, REQ-2.3
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
@CRUD.For(models.Category.class)
public class Categories extends CRUD {
}
