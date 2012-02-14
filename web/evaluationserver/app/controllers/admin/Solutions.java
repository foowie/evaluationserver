package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import models.Role;
import models.SolutionFile;
import play.mvc.Before;
import play.mvc.Router;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Solutions extends CRUD {

	@Before(only = "list")
	public static void sortList() {
		if(!params._contains("orderBy")) {
			params.put("orderBy", "id");
			params.put("order", "DESC");
		}
	}
	
	@Before(only="save")
	public static void beforeSave(Long id, java.io.File file) throws IOException {
        models.Solution solution = models.Solution.findById(id);
		notFoundIfNull(solution);

		if(file != null) {
			SolutionFile solutionFile = new SolutionFile(file);
			solutionFile.save();
			params.put("object.file.id", solutionFile.getId().toString());
		}
	}	
	
    public static void blank() throws Exception {
		redirect(Router.getFullUrl("admin.Solutions.list"));
	}
    public static void create() throws Exception {
		redirect(Router.getFullUrl("admin.Solutions.list"));
	}
	
	public static void unevaluate(Long id) {
        models.Solution solution = models.Solution.findById(id);
		notFoundIfNull(solution);
		solution.evaluated = null;
		solution.evaluationLockUntil = null;
		solution.log = null;
		solution.memory = null;
		solution.systemReply = null;
		solution.timeLength = null;
		solution.save();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		redirect(Router.getFullUrl("admin.Solutions.show", parameters));
	}
	
}
