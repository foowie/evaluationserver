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
import javax.persistence.FetchType;

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
	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
	public List<Competition> competitions;
	
	@JoinTable(name = "UserUserGroup", joinColumns = {
    	@JoinColumn(name = "groupId", referencedColumnName = "id")}, inverseJoinColumns = {
    	@JoinColumn(name = "userId", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
	public List<Contestant> users;


	@Override
	public String toString() {
		return name;
	}
	
}
