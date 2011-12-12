package evaluationserver.server.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "File")
@XmlRootElement
public class File implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "pathName", length = 300)
	private String path;
	@Basic(optional = false)
	@Column(name = "fileSize", nullable = false)
	private long size;
	@JoinColumn(name = "fileData", referencedColumnName = "id", nullable = true)
	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private FileData data;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resultResolver", fetch = FetchType.LAZY)
//	private List<Task> taskList;
//	@OneToMany(mappedBy = "outputData", fetch = FetchType.LAZY)
//	private List<Task> taskList1;
//	@OneToMany(mappedBy = "inputData", fetch = FetchType.LAZY)
//	private List<Task> taskList2;
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "file", fetch = FetchType.LAZY)
//	private List<Solution> solutionList;
	public File() {
	}

	public File(Integer id) {
		this.id = id;
	}

	public File(Integer id, String name, int size) {
		this.id = id;
		this.name = name;
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public File setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public File setPath(String path) {
		this.path = path;
		return this;
	}

	public long getSize() {
		return size;
	}

	public File setSize(long size) {
		this.size = size;
		return this;
	}

	public FileData getData() {
		return data;
	}

	public File setData(FileData data) {
		this.data = data;
		return this;
	}
	
	public File setData(byte[] data) {
		if(this.data == null)
			this.data = new FileData(null, data);
		else
			this.data.setFileData(data);
		return this;
	}

//	@XmlTransient
//	public List<Task> getTaskList() {
//		return taskList;
//	}
//
//	public File setTaskList(List<Task> taskList) {
//		this.taskList = taskList;
//		return this;
//	}
//
//	@XmlTransient
//	public List<Task> getTaskList1() {
//		return taskList1;
//	}
//
//	public File setTaskList1(List<Task> taskList1) {
//		this.taskList1 = taskList1;
//		return this;
//	}
//
//	@XmlTransient
//	public List<Task> getTaskList2() {
//		return taskList2;
//	}
//
//	public File setTaskList2(List<Task> taskList2) {
//		this.taskList2 = taskList2;
//		return this;
//	}
//
//	@XmlTransient
//	public List<Solution> getSolutionList() {
//		return solutionList;
//	}
//
//	public File setSolutionList(List<Solution> solutionList) {
//		this.solutionList = solutionList;
//		return this;
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
		if (!(object instanceof File)) {
			return false;
		}
		File other = (File) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.File[ id=" + id + " ]";
	}
}
