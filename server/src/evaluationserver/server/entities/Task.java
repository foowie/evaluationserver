package evaluationserver.server.entities;

import java.io.Serializable;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Task")
@XmlRootElement
public class Task implements Serializable {

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
	@Lob
	@Column(name = "description", nullable = false, length = 65535)
	private String description;
	
	@Basic(optional = false)
	@Column(name = "timeLimit", nullable = false)
	private int timeLimit;
	
	@Basic(optional = false)
	@Column(name = "sourceLimit", nullable = false)
	private int sourceLimit;
	
	@Basic(optional = false)
	@Column(name = "memoryLimit", nullable = false)
	private int memoryLimit;
	
	@Basic(optional = false)
	@Column(name = "outputLimit", nullable = false)
	private int outputLimit;
	
	@Lob
	@Column(name = "sampleInput", length = 65535)
	private String sampleInput;
	
	@Lob
	@Column(name = "sampleOutput", length = 65535)
	private String sampleOutput;
	
	@Basic(optional = false)
	@Column(name = "created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	
	@JoinColumn(name = "resultResolver", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private File resultResolver;
	
	@JoinColumn(name = "outputData", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private File outputData;
	
	@JoinColumn(name = "inputData", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private File inputData;
	
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.LAZY)
//	private List<Solution> solutionList;

	public Task() {
	}

	public Task(Integer id) {
		this.id = id;
	}

	public Task(Integer id, String name, String description, int timeLimit, int sourceLimit, int memoryLimit, int outputLimit, Date created) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.timeLimit = timeLimit;
		this.sourceLimit = sourceLimit;
		this.memoryLimit = memoryLimit;
		this.outputLimit = outputLimit;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public Task setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Task setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Task setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public Task setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
		return this;
	}

	public int getSourceLimit() {
		return sourceLimit;
	}

	public Task setSourceLimit(int sourceLimit) {
		this.sourceLimit = sourceLimit;
		return this;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public Task setMemoryLimit(int memoryLimit) {
		this.memoryLimit = memoryLimit;
		return this;
	}
	
	public int getOutputLimit() {
		return outputLimit;
	}
	
	public Task setOutputLimit(int outputLimit) {
		this.outputLimit = outputLimit;
		return this;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public Task setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
		return this;
	}

	public String getSampleOutput() {
		return sampleOutput;
	}

	public Task setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public Task setCreated(Date created) {
		this.created = created;
		return this;
	}


	public File getResultResolver() {
		return resultResolver;
	}

	public Task setResultResolver(File resultResolver) {
		this.resultResolver = resultResolver;
		return this;
	}

	public File getOutputData() {
		return outputData;
	}

	public Task setOutputData(File outputData) {
		this.outputData = outputData;
		return this;
	}

	public File getInputData() {
		return inputData;
	}

	public Task setInputData(File inputData) {
		this.inputData = inputData;
		return this;
	}

//	@XmlTransient
//	public List<Solution> getSolutionList() {
//		return solutionList;
//	}
//
//	public Task setSolutionList(List<Solution> solutionList) {
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
		if (!(object instanceof Task)) {
			return false;
		}
		Task other = (Task) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "evaluationserver.entities.Task[ id=" + id + " ]";
	}
}
