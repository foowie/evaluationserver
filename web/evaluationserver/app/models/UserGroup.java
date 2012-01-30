package models;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Exclude;

@Entity
@Table(name = "UserGroup")
public class UserGroup extends Model {

	@Required
	@MaxSize(100)
	@Basic(optional = false)
    @Column(name = "name")
	public String name;	
	
	@MaxSize(65536)
	@Basic(optional = false)
    @Lob
    @Column(name = "description")
	public String description;	
	
	@Exclude
	@ManyToMany(mappedBy = "groups")
	public List<Competition> competitions;
	
	@JoinTable(name = "UserUserGroup", joinColumns = {
    	@JoinColumn(name = "groupId", referencedColumnName = "id")}, inverseJoinColumns = {
    	@JoinColumn(name = "userId", referencedColumnName = "id")})
    @ManyToMany
	public List<Contestant> users;


	@Basic(optional = false)
    @Column(name = "active")
	public boolean active = true;	
	
	@Override
	public String toString() {
		return name;
	}
	
}
