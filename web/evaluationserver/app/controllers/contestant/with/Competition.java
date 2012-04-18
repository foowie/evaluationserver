package controllers.contestant.with;

import controllers.Security;
import models.Contestant;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * Competition helper
 * @author Daniel Robenek <danrob@seznam.cz>
 */
public class Competition extends Controller {

	/**
	 * Check user's authorization to this competition
	 * @param competitionId 
	 */
	@Before
	public static void checkCompetition(Long competitionId) {
		if (competitionId == null) {
			Security.onCheckFailed();
		}
		if (!models.Competition.hasAccess(competitionId, Contestant.getLoggedUser())) {
			Security.onCheckFailed();
		}

		models.Competition competition = models.Competition.findById(competitionId);
		renderArgs.put("competition", competition);
	}
}
