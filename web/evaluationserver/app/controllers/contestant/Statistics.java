package controllers.contestant;

import java.util.List;
import javax.persistence.Query;
import models.Contestant;
import models.Role;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class, 
	controllers.contestant.with.Competition.class, 
	controllers.contestant.with.Menu.class
})
public class Statistics extends Controller {

	public static void index(long competitionId) {
		
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
		query.setParameter("competition", renderArgs.get("competition"));
		query.setParameter("user", Contestant.getLoggedUser());
		List statistics = query.getResultList();

		render(statistics);
	}	
	
}
