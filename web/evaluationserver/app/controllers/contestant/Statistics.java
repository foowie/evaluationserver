package controllers.contestant;

import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import models.Contestant;
import models.Role;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.With;
import services.competition.ContestantsResult;
import services.competition.ContestantsStatistics;
import models.Competition;

/**
 * User's statistics
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	controllers.contestant.with.Competition.class, 
	controllers.contestant.with.Menu.class
})
public class Statistics extends Controller {

	/**
	 * Statistics by task
	 * @param competitionId 
	 */
	public static void index(long competitionId) {
		Competition competition = (Competition)renderArgs.get("competition");
		
		Query query = JPA.em().createQuery(
			"SELECT t.id, t.name, " +
				"(SELECT COUNT(s) FROM Solution s JOIN s.systemReply sr WHERE s.competition = :competition AND s.task = t.id AND sr.accepting = true AND s.user = :user) AS accepted, " +
				"(SELECT COUNT(s) FROM Solution s JOIN s.systemReply sr WHERE s.competition = :competition AND  s.task = t.id AND sr.accepting = false AND s.user = :user) AS wrong, " +
				"(SELECT COUNT(s) FROM Solution s WHERE s.competition = :competition AND s.task = t.id AND s.evaluated IS NULL AND s.user = :user) AS inQueue " +
			"FROM Task t " +
				"JOIN t.competitions c " +
			"WHERE c = :competition " +
			"ORDER BY t.name DESC" 
		);
		query.setParameter("competition", competition);
		query.setParameter("user", Contestant.getLoggedUser());
		List statistics = query.getResultList();

		ContestantsStatistics cs = new ContestantsStatistics();
		if(cs.hasStatistics(competition)) {
			Collection<ContestantsResult> contestantsStatistics = cs.getStatistics(competition, false);
			renderArgs.put("contestantsStatistics", contestantsStatistics);
		} else {
			renderArgs.put("contestantsStatistics", null);
		}
		
		Contestant contestant = Contestant.getLoggedUser();
		render(statistics, contestant);
	}	
	
}
