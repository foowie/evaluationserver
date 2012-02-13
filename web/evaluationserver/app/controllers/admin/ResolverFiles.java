package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import java.util.List;
import java.util.Map;
import models.Role;
import play.mvc.After;
import play.mvc.With;
import play.data.validation.Error;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.Files.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class ResolverFiles extends CRUD {
//	@After
//	public static void rollbackOnValidationError() {
//		
//		
//		if(validation.hasErrors()) {
//			System.out.println("------------------------------------------\nErrors:");
//			Map<String, List<Error>> errorsMap = validation.errorsMap();
//			for(String field : errorsMap.keySet()) {
//				System.out.println("\"" + field + "\"");
//				for(Error error : errorsMap.get(field)) {
//					System.out.println(" - " + error.message());
//				}
//			}
//				
//		}
//	}
}
