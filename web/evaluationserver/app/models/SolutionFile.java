package models;

import java.io.IOException;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("4")
public class SolutionFile extends File {

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "file")
	public Solution solution;

	public SolutionFile() {
	}
	
	public SolutionFile(java.io.File file) throws IOException {
		super(file);
		title = file.getName();
	}

}
