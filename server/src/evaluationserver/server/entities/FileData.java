package evaluationserver.server.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "FileData")
public class FileData implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Basic(optional = false)
    @Lob
    @Column(name = "fileData")
	private byte[] fileData;
//	@OneToMany(mappedBy = "fileData")
//	private Collection<File> fileCollection;

	public FileData() {
	}

	public FileData(Integer id) {
		this.id = id;
	}

	public FileData(Integer id, byte[] fileData) {
		this.id = id;
		this.fileData = fileData;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

//	public Collection<File> getFileCollection() {
//		return fileCollection;
//	}
//
//	public void setFileCollection(Collection<File> fileCollection) {
//		this.fileCollection = fileCollection;
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
		if (!(object instanceof FileData)) {
			return false;
		}
		FileData other = (FileData) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.server.entities.FileData[ id=" + id + " ]";
	}
	
}
