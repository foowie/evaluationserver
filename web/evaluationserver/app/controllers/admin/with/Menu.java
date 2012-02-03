package controllers.admin.with;

import java.util.Map;
import play.mvc.Before;
import play.mvc.Controller;
import navigation.Navigation;
import navigation.ContextedMenuItem;

public class Menu extends Controller {

	@Before
	public static void menu() {
		Navigation.getMenuContext().setActiveLabel(Controller.request.controllerClass.getSimpleName());

		ContextedMenuItem menu = Navigation.getMenu("admin");
		String[] useParams = new String[] {};

		Map<String, Object> substitutions = Navigation.getMenuContext().substitutions;
		for(String useParam : useParams)
			if(params._contains(useParam))
				substitutions.put(useParam, params.get(useParam));
				
		
		renderArgs.put("menu", menu);
	}
	
}
