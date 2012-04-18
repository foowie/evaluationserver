package models;

import java.io.IOException;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * File that contains data of solution
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@DiscriminatorValue("4")
public class SolutionFile extends File {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "file")
	public Solution solution;

	public SolutionFile() {
	}

	public SolutionFile(java.io.File file) throws IOException {
		super(file);
		title = file.getName();
	}
}
