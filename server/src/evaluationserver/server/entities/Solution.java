package evaluationserver.server.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Solution")
@XmlRootElement
public class Solution implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Basic(optional = false)
	@Column(name = "dateInsert", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name = "dateEvaluated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEvaluated;
	
	@Column(name = "timeLength")
	private int time;
	
	@Column(name = "memory")
	private int memory;
	
	@JoinColumn(name = "language", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Language language;
	
	@JoinColumn(name = "systemReply", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private SystemReply systemReply;
	
	@JoinColumn(name = "file", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private File file;
	
	@JoinColumn(name = "competition", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Competition competition;
	
	@JoinColumn(name = "task", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Task task;
	
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User user;
	
	@Column(name = "evaluationLockUntil")
	@Temporal(TemporalType.TIMESTAMP)
	private Date evaluationLockUntil;
	
	public Solution() {
	}

	public Solution(Integer id) {
		this.id = id;
	}

	public Solution(Integer id, Date date) {
		this.id = id;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public Solution setId(Integer id) {
		this.id = id;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public Solution setDate(Date date) {
		this.date = date;
		return this;
	}

	public int getTime() {
		return time;
	}

	public Solution setTime(int time) {
		this.time = time;
		return this;
	}

	public int getMemory() {
		return memory;
	}

	public Solution setMemory(int memory) {
		this.memory = memory;
		return this;
	}

	public Language getLanguage() {
		return language;
	}

	public Solution setLanguage(Language language) {
		this.language = language;
		return this;
	}

	public SystemReply getSystemReply() {
		return systemReply;
	}

	public Solution setSystemReply(SystemReply systemReply) {
		this.systemReply = systemReply;
		return this;
	}

	public File getFile() {
		return file;
	}

	public Solution setFile(File file) {
		this.file = file;
		return this;
	}

	public Competition getCompetition() {
		return competition;
	}

	public Solution setCompetition(Competition competition) {
		this.competition = competition;
		return this;
	}

	public Task getTask() {
		return task;
	}

	public Solution setTask(Task task) {
		this.task = task;
		return this;
	}

	public User getUser() {
		return user;
	}

	public Solution setUser(User user) {
		this.user = user;
		return this;
	}

	public Date getDateEvaluated() {
		return dateEvaluated;
	}

	public Solution setDateEvaluated(Date dateEvaluated) {
		this.dateEvaluated = dateEvaluated;
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
		if (!(object instanceof Solution)) {
			return false;
		}
		Solution other = (Solution) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	public Date getEvaluationLockUntil() {
		return evaluationLockUntil;
	}

	public void setEvaluationLockUntil(Date evaluationLockUntil) {
		this.evaluationLockUntil = evaluationLockUntil;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.Solution[ id=" + id + " ]";
	}
}
