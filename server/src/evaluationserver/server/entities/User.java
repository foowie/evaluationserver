package evaluationserver.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Users")
@XmlRootElement
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "name", length = 200)
	private String name;
	
	@Column(name = "surname", length = 200)
	private String surname;
	
	@Basic(optional = false)
    @Column(name = "login", nullable = false, length = 50)
	private String login;
	
	@Basic(optional = false)
    @Column(name = "password", nullable = false, length = 128)
	private String password;
	
	@Column(name = "email", length = 100)
	private String email;
	
	@Basic(optional = false)
    @Column(name = "active", nullable = false)
	private boolean active;
	
	@Basic(optional = false)
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@JoinTable(name = "UserUserGroup", joinColumns = {
    	@JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
    	@JoinColumn(name = "groupId", referencedColumnName = "id", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
	private List<UserGroup> userGroupList;
	
//	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
//	private List<User> userList;
	
	@JoinColumn(name = "creator", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
	private User creator;
	
	@JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Role role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.LAZY)
	private List<Task> taskList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private List<Solution> solutionList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.LAZY)
	private List<Competition> competitionList ;

	public User() {
	}

	public User(Integer id) {
		this.id = id;
	}

	public User(Integer id, String login, String password, boolean active, Date created) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.active = active;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public User setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public String getLogin() {
		return login;
	}

	public User setLogin(String login) {
		this.login = login;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public boolean getActive() {
		return active;
	}

	public User setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public User setCreated(Date created) {
		this.created = created;
		return this;
	}

	@XmlTransient
	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public User setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
		return this;
	}
	
	public User addUserGroup(UserGroup group) {
		if(this.getUserGroupList() == null)
			this.setUserGroupList(new ArrayList<UserGroup>());
		getUserGroupList().add(group);
		if(group.getUserList() == null)
			group.setUserList(new ArrayList<User>());
		group.getUserList().add(this);
		return this;
	}

//	@XmlTransient
//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public User setUserList(List<User> userList) {
//		this.userList = userList;
//		return this;
//	}

	public User getCreator() {
		return creator;
	}

	public User setCreator(User creator) {
		this.creator = creator;
		return this;
	}

	public Role getRole() {
		return role;
	}

	public User setRole(Role role) {
		this.role = role;
		return this;
	}

	@XmlTransient
	public List<Task> getTaskList() {
		return taskList;
	}

	public User setTaskList(List<Task> taskList) {
		this.taskList = taskList;
		return this;
	}

	@XmlTransient
	public List<Solution> getSolutionList() {
		return solutionList;
	}

	public User setSolutionList(List<Solution> solutionList) {
		this.solutionList = solutionList;
		return this;
	}

	@XmlTransient
	public List<Competition> getCompetitionList() {
		return competitionList;
	}

	public User setCompetitionList(List<Competition> competitionList) {
		this.competitionList = competitionList;
		return this;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof User)) {
			return false;
		}
		User other = (User) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.User[ id=" + id + " ]";
	}
	
}
