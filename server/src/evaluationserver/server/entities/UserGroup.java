package evaluationserver.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "UserGroup")
@XmlRootElement
public class UserGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;
	
	@Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
	private String name;
	
	@Basic(optional = false)
    @Column(name = "active", nullable = false)
	private boolean active;
	
	@Basic(optional = false)
    @Lob
    @Column(name = "description", nullable = false, length = 65535)
	private String description;
	
//	@ManyToMany(mappedBy = "userGroupList", fetch = FetchType.LAZY)
//	private List<Competition> competitionList;
	
	@ManyToMany(mappedBy = "userGroupList", fetch = FetchType.LAZY)
	private List<User> userList;
	

	public UserGroup() {
	}

	public UserGroup(Integer id) {
		this.id = id;
	}

	public UserGroup(Integer id, String name, boolean active, String description) {
		this.id = id;
		this.name = name;
		this.active = active;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	@XmlTransient
//	public List<Competition> getCompetitionList() {
//		return competitionList;
//	}
//
//	public void setCompetitionList(List<Competition> competitionList) {
//		this.competitionList = competitionList;
//	}

	@XmlTransient
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public UserGroup addUser(User user) {
		user.addUserGroup(this);
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
		if (!(object instanceof UserGroup)) {
			return false;
		}
		UserGroup other = (UserGroup) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.UserGroup[ id=" + id + " ]";
	}
	
}
