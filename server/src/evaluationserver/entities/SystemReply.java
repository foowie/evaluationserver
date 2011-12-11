package evaluationserver.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "SystemReply")
@XmlRootElement
public class SystemReply implements Serializable {
	public static final String COMPILATION_ERROR = "compile-error";
	
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
    @Column(name = "accepting", nullable = false)
	private boolean accepting;
	
	@Basic(optional = false)
    @Column(name = "keyName", nullable = false, length = 200)
	private String key;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "systemReply", fetch = FetchType.LAZY)
//	private List<Solution> solutionList;

	public SystemReply() {
	}

	public SystemReply(Integer id) {
		this.id = id;
	}

	public SystemReply(Integer id, String name, boolean accepting, String key) {
		this.id = id;
		this.name = name;
		this.accepting = accepting;
		this.key = key;
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

	public boolean getAccepting() {
		return accepting;
	}

	public void setAccepting(boolean accepting) {
		this.accepting = accepting;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

//	@XmlTransient
//	public List<Solution> getSolutionList() {
//		return solutionList;
//	}
//
//	public void setSolutionList(List<Solution> solutionList) {
//		this.solutionList = solutionList;
//	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof SystemReply)) {
			return false;
		}
		SystemReply other = (SystemReply) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.SystemReply[ id=" + id + " ]";
	}
	
}
