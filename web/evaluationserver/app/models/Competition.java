package models;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;
import javax.persistence.JoinTable;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import controllers.CRUD.Exclude;
import javax.persistence.FetchType;
import javax.persistence.Query;
import play.data.binding.As;
import play.db.jpa.JPA;

@Entity
@Table(name = "Competition")
public class Competition extends Model {
	
	@Exclude
	@Basic(optional = false)
	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	public Date created;

	@Required
	@MaxSize(100)	
	@Basic(optional = false)
	@Column(name = "name")
	public String name;
	
	@As(lang={"*"}, value={"yyyy-MM-dd HH:mm"}) 
	@Column(name = "startDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date startDate;
	
	@As(lang={"*"}, value={"yyyy-MM-dd HH:mm"}) 
	@Column(name = "stopDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date stopDate;
	
	@Basic(optional = false)
	@Column(name = "timePenalization")
	public int timePenalization; // [min]

	
	@JoinTable(name = "CompetitionGroup", joinColumns = {
    	@JoinColumn(name = "groupId", referencedColumnName = "id")}, inverseJoinColumns = {
    	@JoinColumn(name = "competitionId", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
	public List<UserGroup> groups;
	
	@JoinTable(name = "CompetitionTask", joinColumns = {
    	@JoinColumn(name = "competitionId", referencedColumnName = "id")}, inverseJoinColumns = {
    	@JoinColumn(name = "taskId", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
	public List<Task> tasks;
	
	@Exclude
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "competition")
	public List<Solution> solutios;
	
	@Exclude
	@Required
	@JoinColumn(name = "creator", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Admin creator;


	@Basic(optional = false)
	@Column(name = "active")
	public boolean active = true;	
	
	@Override
	public void _save() {
		if(created == null)
			created = new Date();
		super._save();
	}	
	
	public static boolean hasAccess(Long competitionId, Contestant contestant) {
		Query q = JPA.em().createQuery(
			"SELECT COUNT(c) " + 
			"FROM Competition c " + 
				"JOIN c.groups g " +
				"JOIN g.users u " +
			"WHERE c.id=:id AND c.active=true AND g.active=true " +
				"AND (c.startDate IS NULL OR c.startDate <= CURRENT_TIMESTAMP) " + 
				"AND (c.stopDate IS NULL OR c.stopDate >= CURRENT_TIMESTAMP) " +
				"AND u=:user"
		);
		q.setParameter("id", competitionId);
		q.setParameter("user", User.getLoggedUser());
		return ((Long)q.getSingleResult()) > 0;
	}
	
	public static List<Competition> findContestantsCompetitions(Contestant contestant) {
		Query q = JPA.em().createQuery(
			"SELECT c " + 
			"FROM Competition c " + 
				"JOIN c.groups g " +
				"JOIN g.users u " +
			"WHERE c.active=true AND g.active=true " +
				"AND (c.startDate IS NULL OR c.startDate <= CURRENT_TIMESTAMP) " + 
				"AND (c.stopDate IS NULL OR c.stopDate >= CURRENT_TIMESTAMP) " +
				"AND u=:user"
		);
		q.setParameter("user", User.getLoggedUser());
		return (List<models.Competition>)q.getResultList();		
	}
	
	@Override
	public String toString() {
		return name;
	}
}
