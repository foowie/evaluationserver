package models;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import play.data.validation.MaxSize;
import play.data.validation.Required;

/**
 * Solution of task
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@Entity
@Table(name = "Solution")
public class Solution extends Model {

	@Basic(optional = false)
	@Column(name = "dateInsert")
	@Temporal(TemporalType.TIMESTAMP)
	public Date created;
	@Column(name = "dateEvaluated")
	@Temporal(TemporalType.TIMESTAMP)
	public Date evaluated;
	@Column(name = "evaluationLockUntil")
	@Temporal(TemporalType.TIMESTAMP)
	public Date evaluationLockUntil;
	@MaxSize(1024)
	@Column(name = "log")
	public String log;
	@Column(name = "memory")
	public Integer memory;
	@Column(name = "timeLength")
	public Integer timeLength;
	@Required
	@JoinColumn(name = "user", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Contestant user;
	@Required
	@JoinColumn(name = "task", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Task task;
	@JoinColumn(name = "systemReply", referencedColumnName = "id")
	@ManyToOne
	public SystemReply systemReply;
	@Required
	@JoinColumn(name = "language", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Language language;
	@Required
	@JoinColumn(name = "file", referencedColumnName = "id")
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public SolutionFile file;
	@Required
	@JoinColumn(name = "competition", referencedColumnName = "id")
	@ManyToOne(optional = false)
	public Competition competition;

	@Override
	public void _save() {
		if (created == null) {
			created = new Date();
		}
		super._save();
	}

	/**
	 * Remove evaluation of this solution, need to be saved
	 */
	public void unevaluate() {
		evaluated = null;
		evaluationLockUntil = null;
		log = null;
		memory = null;
		systemReply = null;
		timeLength = null;
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
