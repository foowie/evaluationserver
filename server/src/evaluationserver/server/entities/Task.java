package evaluationserver.server.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Basic(optional = false)
	@Column(name = "timeLimit", nullable = false)
	private int timeLimit;
	
	@Basic(optional = false)
	@Column(name = "memoryLimit", nullable = false)
	private int memoryLimit;
	
	@Basic(optional = false)
	@Column(name = "outputLimit", nullable = false)
	private int outputLimit;
	
	@JoinColumn(name = "resultResolver", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private File resultResolver;
	
	@JoinColumn(name = "outputData", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private File outputData;
	
	@JoinColumn(name = "inputData", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private File inputData;
	
	
	public Task() {
	}

	public Task(Integer id) {
		this.id = id;
	}

	public Task(Integer id, int timeLimit, int memoryLimit, int outputLimit) {
		this.id = id;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.outputLimit = outputLimit;
	}

	public Integer getId() {
		return id;
	}

	public Task setId(Integer id) {
		this.id = id;
		return this;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public Task setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
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
