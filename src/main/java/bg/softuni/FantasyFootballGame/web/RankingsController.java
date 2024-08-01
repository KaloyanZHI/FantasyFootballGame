package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RankingsController {
    private final FantasyTeamService fantasyTeamService;

    public RankingsController(FantasyTeamService fantasyTeamService) {
        this.fantasyTeamService = fantasyTeamService;
    }

    @GetMapping("/rankings")
    public ModelAndView getRankings() throws IOException {
        ModelAndView modelAndView = new ModelAndView("rankings");
        List<FantasyTeam> rankedFantasyTeams = this.fantasyTeamService.findAllFantasyTeams()
                .stream()
                .sorted((Comparator.comparing(FantasyTeam::getTotalTeamPoints)).reversed())
                .toList();

        modelAndView.addObject("rankedTeams", rankedFantasyTeams);

        return modelAndView;
    }
}
