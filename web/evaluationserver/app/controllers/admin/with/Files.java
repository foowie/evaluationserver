package controllers.admin.with;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import models.File;
import models.FileData;
import play.db.jpa.JPA;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;

public class Files extends Controller {

	@Before
	public static void beforeCreate(java.io.File data) throws Exception {
		String action = request.action.substring(request.action.lastIndexOf(".") + 1);
		if(!action.equals("create"))
			return;
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

	@After
	public static void afterDelete() throws Exception {
		String action = request.action.substring(request.action.lastIndexOf(".") + 1);
		if(!action.equals("delete"))
			return;
		JPA.em().createQuery("DELETE FROM FileData fd WHERE fd NOT IN (SELECT f.data FROM File f)").executeUpdate();
	}
	
	
	public static void download(Long id) throws Exception {
        File file = File.findById(id);
        notFoundIfNull(file);
		renderBinary(new ByteArrayInputStream(file.data.data), file.name, file.size);
	}
}
