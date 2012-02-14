package models;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "Category")
public class Category extends Model {
	
	@Required
	@MaxSize(200)
	@Basic(optional = false)
    @Column(name = "name")
	public String name;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	public List<Task> tasks;


	@Override
	public String toString() {
		return name;
	}
	
}
