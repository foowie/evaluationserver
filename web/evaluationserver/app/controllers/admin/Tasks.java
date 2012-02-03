package controllers.admin;

import controllers.CRUD;
import controllers.Check;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import models.InputFile;
import models.OutputFile;
import models.Role;
import models.User;
import play.data.validation.Error;
import play.db.jpa.JPA;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.With;

@Check(Role.Check.ADMIN)
@With({
	controllers.Secure.class,
	controllers.admin.with.CRUDSearch.class,
	controllers.admin.with.Menu.class
})
public class Tasks extends CRUD {
	
	@Before(only="create")
	public static void beforeCreate(java.io.File inputData, java.io.File outputData) throws IOException {
		params.put("object.creator.id", User.getLoggedUser().getId().toString());
		validation.required(inputData);
		
		if(!validation.hasErrors()) {
			InputFile inputFile = new InputFile(inputData);
			inputFile.save();
			params.put("object.inputData.id", inputFile.getId().toString());
			
			if(outputData != null) {
				OutputFile outputFile = new OutputFile(outputData);
				outputFile.save();
				params.put("object.outputData.id", outputFile.getId().toString());
			}
		}
	}
	
	@Before(only="save")
	public static void beforeSave(Long id, java.io.File inputData, java.io.File outputData, boolean removeOutputData) throws IOException {
        models.Task task = models.Task.findById(id);
		notFoundIfNull(task);

		if(removeOutputData)
			task.deleteOutputData();
		
		if(inputData != null) {
			InputFile inputFile = new InputFile(inputData);
			inputFile.save();
			params.put("object.inputData.id", inputFile.getId().toString());
		}
		if(outputData != null) {
			OutputFile outputFile = new OutputFile(outputData);
			outputFile.save();
			params.put("object.outputData.id", outputFile.getId().toString());
		}
	}
	
	@After(only={"save", "delete"})
	public static void afterSaveAndDelete() {
		InputFile.deleteUnused();
		OutputFile.deleteUnused();
	}
	
	
	@After
	public static void rollbackOnValidationError() {
		if(validation.hasErrors())
			JPA.setRollbackOnly();
	}
	
}
