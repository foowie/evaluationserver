package controllers.contestant.with;

import controllers.Security;
import models.Contestant;
import play.mvc.Before;
import play.mvc.Controller;

public class Competition extends Controller {
	
	@Before
	public static void checkCompetition(Long competitionId) {
		if(competitionId == null)
			Security.onCheckFailed();
		if(!models.Competition.hasAccess(competitionId, Contestant.getLoggedUser()))
			Security.onCheckFailed();
		
		models.Competition competition = models.Competition.findById(competitionId);
		renderArgs.put("competition", competition);
	}
	
}
