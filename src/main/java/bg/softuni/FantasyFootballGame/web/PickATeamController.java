package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class PickATeamController {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    private final UserService userService;


    public PickATeamController(RealTeamService realTeamService, PlayerService playerService, UserService userService, FantasyTeamService fantasyTeamService) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
        this.userService = userService;
    }

    @GetMapping("/pick-team")
    public ModelAndView pickATeam(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("pick-a-team");
        List<RealTeam> realTeams = this.realTeamService.findAllRealTeams();
        List<Player> players = this.playerService.findAllPlayers();
        double userBudget = this.userService.findUserBudget(principal);
        int playersCount = this.userService.findByUsername(principal.getName()).getFantasyTeam().getPlayers().size();

        modelAndView.addObject("realTeams", realTeams);
        modelAndView.addObject("realPlayers", players);
        modelAndView.addObject("playersCount", playersCount);
        modelAndView.addObject("budget", userBudget);

        return modelAndView;
    }


}
