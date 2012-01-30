package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "SystemReply")
public class SystemReply extends Model {
	
	
	@Basic(optional = false)
    @Column(name = "accepting")
	public boolean accepting;
	
	@Required
	@MaxSize(2)
	@Basic(optional = false)
    @Column(name = "keyName")
	public String key;
	
	@Required
	@MaxSize(100)
	@Basic(optional = false)
    @Column(name = "name")
	public String name;
	
//	@OneToMany(mappedBy = "systemReply")
//	public List<Solution> solutionList;

	@Override
	public String toString() {
		return name;
	}
	
}
