package models;

import java.io.IOException;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import play.db.jpa.JPA;

@Entity
@DiscriminatorValue("2")
public class InputFile extends File {

	public InputFile(java.io.File file) throws IOException {
		super(file);
		title = file.getName();
	}

	public InputFile() {
	}
	
	
	public static void deleteUnused() {
		List<InputFile> files = JPA.em().createQuery("SELECT f FROM InputFile f WHERE f.id NOT IN(SELECT ff.id FROM Task t JOIN t.inputData ff))").getResultList();
		for(InputFile file : files)
			file.delete();
	}
	
}
