package controllers;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import models.File;
import models.FileData;
import play.db.jpa.JPA;
import play.mvc.After;
import play.mvc.Before;

public class BaseFiles extends CRUD {

	@Before(only = "create")
	public static void beforeCreate(java.io.File data) throws Exception {
		if (validation.required(data).message("crud.help.required").ok) {
			final long size = data.length();
			if (validation.max(size, Integer.MAX_VALUE).ok) {
				final byte[] fileData = new byte[(int)size];
				if(new FileInputStream(data).read(fileData) != size)
					throw new Exception("Upload failed");
				FileData fd = new FileData(fileData);
				fd.create();
				params.put("object.size", Long.toString(size));
				params.put("object.name", data.getName());
				params.put("object.data.id", Long.toString(fd.id));
			}
		}
	}

	@After(only = "delete")
	public static void afterDelete() throws Exception {
		JPA.em().createQuery("DELETE FROM FileData fd WHERE fd NOT IN (SELECT f.data FROM File f)").executeUpdate();
	}
	
	
	public static void download(Long id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        File file = (File)type.findById(id);
        notFoundIfNull(file);
		renderBinary(new ByteArrayInputStream(file.data.data), file.name, file.size);
	}
}
