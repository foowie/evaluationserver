package models;

import java.io.IOException;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import play.db.jpa.JPA;

/**
 * File with output data of task
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@DiscriminatorValue("3")
public class OutputFile extends File {

	public OutputFile(java.io.File file) throws IOException {
		super(file);
		title = file.getName();
	}

	public OutputFile() {
	}

	/**
	 * Remove all unused output files
	 */
	public static void deleteUnused() {
		List<OutputFile> files = JPA.em().createQuery("SELECT f FROM OutputFile f WHERE f.id NOT IN(SELECT ff.id FROM Task t JOIN t.outputData ff))").getResultList();
		for (OutputFile file : files) {
			file.delete();
		}
	}
}
