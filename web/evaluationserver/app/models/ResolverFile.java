package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * File with resolver of task output data
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@DiscriminatorValue("1")
public class ResolverFile extends File {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resultResolver")
	public List<Task> tasks;
}
