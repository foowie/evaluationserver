package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import controllers.CRUD.Exclude;
import javax.persistence.FetchType;

@Entity
@DiscriminatorValue("2")
public class Contestant extends User {
	
	@Exclude
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public List<Solution> solutions;	
	
	public static Contestant getLoggedUser() {
		return (Contestant)User.getLoggedUser();
	}

}
