package controllers.contestant;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import models.Contestant;
import models.Language;
import models.Role;
import models.Solution;
import models.SolutionFile;
import models.Task;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Submit new solution
 * @author Daniel Robenek <danrob@seznam.cz>
 */
@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class,
	controllers.contestant.with.Competition.class,
	controllers.contestant.with.Menu.class,
	controllers.contestant.with.SubmitNotification.class
})
public class Submit extends Controller {

	/**
	 * Show solutions
	 * @param competitionId 
	 */
	public static void index(long competitionId) {
		List<Language> languages = Language.findAll();
		List<Task> tasks = ((models.Competition) renderArgs.get("competition")).tasks;

		render(languages, tasks);
	}

	/**
	 * Post new solution
	 * @param competitionId
	 * @param solution
	 * @param taskId
	 * @param languageId
	 * @throws IOException 
	 */
	public static void submit(long competitionId, @Required File solution, @Required long taskId, @Required long languageId) throws IOException {
		models.Task task = models.Task.findById(taskId);
		models.Language lang = models.Language.findById(languageId);
		if (task == null || !task.isInCompetition(competitionId)) {
			validation.addError("taskId", "Invalid task");
		}
		if (lang == null) {
			validation.addError("languageId", "Invalid language");
		}
		Contestant loggedUser = Contestant.getLoggedUser();
		Long count = (Long) JPA.em().createQuery("SELECT COUNT(s) FROM Solution s WHERE s.user = :contestant AND s.evaluated IS NULL").setParameter("contestant", loggedUser).getSingleResult();
		validation.max(count, 5).key("error").message("You have reached maximum submitted solution in queue");

		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index(competitionId);
		}

		Solution s = new Solution();
		s.file = new SolutionFile(solution);
		s.competition = (models.Competition) renderArgs.get("competition");
		s.created = new Date();
		s.language = lang;
		s.task = task;
		s.user = loggedUser;
		s.save();
		JPA.em().getTransaction().commit();

		Solutions.index(competitionId, 1);
	}
}
