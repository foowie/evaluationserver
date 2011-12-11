package evaluationserver.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Competition")
@XmlRootElement
public class Competition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	
	@Column(name = "startDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	
	@Column(name = "stopDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date stop;
	
	@Basic(optional = false)
	@Column(name = "timePenalization", nullable = false)
	private int timePenalization;
	
	@Basic(optional = false)
	@Column(name = "active", nullable = false)
	private boolean active;
	
	@Basic(optional = false)
	@Column(name = "created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@JoinTable(name = "CompetitionGroup", joinColumns = {
		@JoinColumn(name = "competitionId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
		@JoinColumn(name = "groupId", referencedColumnName = "id", nullable = false)})
	@ManyToMany(fetch = FetchType.LAZY)
	private List<UserGroup> userGroupList;
	
	@JoinTable(name = "CompetitionTask", joinColumns = {
		@JoinColumn(name = "competitionId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
		@JoinColumn(name = "taskId", referencedColumnName = "id", nullable = false)})
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Task> taskList;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "competition", fetch = FetchType.LAZY)
//	private List<Solution> solutionList;
	
	@JoinColumn(name = "creator", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User creator;

	public Competition() {
	}

	public Competition(Integer id) {
		this.id = id;
	}

	public Competition(Integer id, String name, int timePenalization, boolean active, Date created) {
		this.id = id;
		this.name = name;
		this.timePenalization = timePenalization;
		this.active = active;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public Competition setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Competition setName(String name) {
		this.name = name;
		return this;
	}

	public Date getStart() {
		return start;
	}

	public Competition setStart(Date start) {
		this.start = start;
		return this;
	}

	public Date getStop() {
		return stop;
	}

	public Competition setStop(Date stop) {
		this.stop = stop;
		return this;
	}

	public int getTimePenalization() {
		return timePenalization;
	}

	public Competition setTimePenalization(int timePenalization) {
		this.timePenalization = timePenalization;
		return this;
	}

	public boolean getActive() {
		return active;
	}

	public Competition setActive(boolean active) {
		this.active = active;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public Competition setCreated(Date created) {
		this.created = created;
		return this;
	}

	@XmlTransient
	public List<UserGroup> getUserGroupList() {
		return userGroupList;
	}

	public Competition setUserGroupList(List<UserGroup> userGroupList) {
		this.userGroupList = userGroupList;
		return this;
	}

	public Competition addUserGroup(UserGroup userGroup) {
		if (getUserGroupList() == null) {
			setUserGroupList(new ArrayList<UserGroup>());
		}
		getUserGroupList().add(userGroup);
//		if (userGroup.getCompetitionList() == null) {
//			userGroup.setCompetitionList(new ArrayList<Competition>());
//		}
//		userGroup.getCompetitionList().add(this);
		return this;
	}

	@XmlTransient
	public List<Task> getTaskList() {
		return taskList;
	}

	public Competition setTaskList(List<Task> taskList) {
		this.taskList = taskList;
		return this;
	}

	public Competition addTask(Task task) {
		if (getTaskList() == null) {
			setTaskList(new ArrayList<Task>());
		}
		getTaskList().add(task);
		if (task.getCompetitionList() == null) {
			task.setCompetitionList(new ArrayList<Competition>());
		}
		task.getCompetitionList().add(this);
		return this;
	}

//	@XmlTransient
//	public List<Solution> getSolutionList() {
//		return solutionList;
//	}
//
//	public Competition setSolutionList(List<Solution> solutionList) {
//		this.solutionList = solutionList;
//		return this;
//	}

	public User getCreator() {
		return creator;
	}

	public Competition setCreator(User creator) {
		this.creator = creator;
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
		if (!(object instanceof Competition)) {
			return false;
		}
		Competition other = (Competition) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.Competition[ id=" + id + " ]";
	}
}
