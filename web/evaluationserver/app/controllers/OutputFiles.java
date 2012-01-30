package controllers;

import play.mvc.After;
import play.mvc.Before;
import play.mvc.With;

@With(BaseFiles.class)
public class OutputFiles extends BaseFiles {
	
	@Before(only = "create")
	public static void beforeCreate(java.io.File data) throws Exception {
		BaseFiles.beforeCreate(data);
	}
	
	@After(only = "delete")
	public static void afterDelete() throws Exception {
		BaseFiles.afterDelete();
	}	
	
}
