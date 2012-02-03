package models;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Exclude;
import javax.persistence.OneToOne;
import play.Play;
import play.db.jpa.JPA;

@Entity
@Table(name = "Task")
public class Task extends Model {
	
	@Required
	@MaxSize(100)
	@Basic(optional = false)
    @Column(name = "name")
	public String name;	

	@Required
	@MaxSize(65536)
	@Basic(optional = false)
    @Lob
    @Column(name = "description")
	public String description;
	
	@JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
	public Category category;	
	
	@Exclude
	@Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
	public Date created;
	
	@Required
	@Basic(optional = false)
    @Column(name = "timeLimit")
	public int timeLimit;	

	@Required
	@Basic(optional = false)
    @Column(name = "sourceLimit")
	public int sourceLimit;
	
	@Required
	@Basic(optional = false)
    @Column(name = "memoryLimit")
	public int memoryLimit;
	
	@Required
	@Basic(optional = false)
    @Column(name = "outputLimit")
	public int outputLimit;
	
	@MaxSize(65536)
	@Lob
    @Column(name = "sampleInput")
	public String sampleInput;
	
	@MaxSize(65536)
	@Lob
    @Column(name = "sampleOutput")
	public String sampleOutput;
	
	@Exclude
	@ManyToMany(mappedBy = "tasks")
	public List<Competition> competitions;
	
	@Required
	@JoinColumn(name = "resultResolver", referencedColumnName = "id")
    @OneToOne(optional = false)
	public ResolverFile resultResolver;
	
	@Required
	@JoinColumn(name = "inputData", referencedColumnName = "id")
    @OneToOne(optional = false)
	public InputFile inputData;
	
	@JoinColumn(name = "outputData", referencedColumnName = "id")
    @OneToOne
	public OutputFile outputData;
	
	@Exclude
	@Required
	@JoinColumn(name = "creator", referencedColumnName = "id")
    @ManyToOne(optional = false)
	public Admin creator;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
	public List<Solution> solutions;

	public Task() {
		timeLimit = Integer.parseInt(Play.configuration.getProperty("task.default.timeLimit", "0"));
		memoryLimit = Integer.parseInt(Play.configuration.getProperty("task.default.memoryLimit", "0"));
		sourceLimit = Integer.parseInt(Play.configuration.getProperty("task.default.sourceLimit", "0"));
		outputLimit = Integer.parseInt(Play.configuration.getProperty("task.default.outputLimit", "0"));
	}
	
	@Override
	public void _save() {
		if(created == null)
			created = new Date();
		super._save();
	}	
	
	public void deleteOutputData() {
		if(outputData != null) {
			File file = outputData;
			outputData = null;
			save();
			file.delete();
		}
	}
	
	public boolean isInCompetition(Long competitionId) {
		Query query = JPA.em().createQuery("SELECT COUNT(c) AS count FROM Competition c JOIN c.tasks t WHERE c.id=:cId AND t.id=:tId");
		query.setParameter("cId", competitionId);
		query.setParameter("tId", this.getId());
		return ((Long)query.getSingleResult()) > 0;
	}
	
	@Override
	public String toString() {
		return category == null ? name : (category.name + " - " + name);
	}
	
}
