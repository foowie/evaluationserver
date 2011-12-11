package evaluationserver.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Languages")
@XmlRootElement
public class Language implements Serializable {
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
	@Column(name = "keyName", nullable = false, length = 100)
	private String key;
	@Basic(optional = false)
	@Column(name = "extension", nullable = false, length = 10)
	private String extension;
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "language", fetch = FetchType.LAZY)
//	private List<Solution> solutionList;

	public Language() {
	}

	public Language(Integer id) {
		this.id = id;
	}

	public Language(Integer id, String name, String extension, String key) {
		this.id = id;
		this.name = name;
		this.extension = extension;
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

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

//	@XmlTransient
//	public List<Solution> getSolutionList() {
//		return solutionList;
//	}
//
//	public void setSolutionList(List<Solution> solutionList) {
//		this.solutionList = solutionList;
//	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
		if (!(object instanceof Language)) {
			return false;
		}
		Language other = (Language) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.Language[ id=" + id + " ]";
	}
}
