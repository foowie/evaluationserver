package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import controllers.CRUD.Exclude;
import javax.persistence.FetchType;

@Entity
@DiscriminatorValue("1")
public class Admin extends User {
	
	@Exclude
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
	public List<Task> tasks;
	
	@Exclude
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
	public List<Competition> competitions;	
	
	public static Admin getLoggedUser() {
		return (Admin)User.getLoggedUser();
	}
}
