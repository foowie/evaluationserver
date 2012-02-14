package models;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Exclude;
import controllers.Security;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Query;
import play.db.jpa.JPA;

@Entity
@Table(name = "User")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="role",
    discriminatorType=DiscriminatorType.INTEGER
)
abstract public class User extends Model {

	@Required
	@MaxSize(50)
	@Basic(optional = false)
    @Column(name = "login")
	public String login;
	
	@Required
	@Password
	@Basic(optional = false)
    @Column(name = "password")
	public String password;	
	
	@Exclude
	@Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
	public Date created;
	
	@MaxSize(100)
	@Email
	@Column(name = "email")
	public String email;
	
	@MaxSize(100)
	@Column(name = "firstName")
	public String firstName;

	
	@MaxSize(100)
	@Column(name = "surname")
	public String surname;
	
	@Exclude
	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	public List<UserGroup> groups;
	
//	@OneToMany(mappedBy = "user")
//	public List<User> userList;
	
	@Exclude
	@JoinColumn(name = "creator", referencedColumnName = "id")
    @ManyToOne( fetch = FetchType.LAZY)
	public User creator;
	
	@Exclude
	@JoinColumn(name = "role", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	public Role role;
	
	@Basic(optional = false)
    @Column(name = "active")
	public boolean active = true;	
	
	@Override
	public void _save() {
		if(created == null)
			created = new Date();
		super._save();
	}

	public void setPassword(String password) {
		if(password.equals(this.password))
			return;
		this.password = Security.hashPassword(password);
	}
	
	public static User findActiveByLogin(String login) {
		Query q = JPA.em().createQuery("SELECT u FROM User u WHERE u.login=:login AND u.active=true");
		q.setParameter("login", login);
		return (User)q.getSingleResult();
	}
	
	public static User getLoggedUser() {
		String username = Security.getUsername();
		if(username == null)
			return null;
		return findActiveByLogin(username);
	}
	
	@Override
	public String toString() {
		return login;
	}
	
}
