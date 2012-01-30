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
import play.mvc.Controller;
import play.mvc.With;

@controllers.Check(Role.Check.CONTESTANT)
@With({
	controllers.Secure.class,
	CompetitionCheck.class,
	Menu.class
})
public class Submit extends Controller {

	public static void index(long competitionId) {
		List<Language> languages = Language.findAll();
		List<Task> tasks = ((models.Competition)renderArgs.get("competition")).tasks;
		
		render(languages, tasks);
	}

	public static void submit(long competitionId, @Required File solution, @Required long taskId, @Required long languageId) throws IOException {
		models.Task task = models.Task.findById(taskId);
		models.Language lang = models.Language.findById(languageId);
		if(task == null || !task.isInCompetition(competitionId))		
			validation.addError("taskId", "Invalid task");
		if(lang == null)
			validation.addError("languageId", "Invalid language");
		
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			index(competitionId);
		}

		Solution s = new Solution();
		s.file = new SolutionFile(solution);
		s.competition = (models.Competition)renderArgs.get("competition");
		s.created = new Date();
		s.language = lang;
		s.task = task;
		s.user = Contestant.getLoggedUser();
		s.save();
		
		Solutions.index(competitionId);
	}
}
