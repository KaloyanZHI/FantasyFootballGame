package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyTeamController {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    public MyTeamController(RealTeamService realTeamService, PlayerService playerService) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
    }

    @GetMapping("/my-team")
    public ModelAndView pickATeam() {
        ModelAndView modelAndView = new ModelAndView("my-team");
        List<RealTeam> realTeams = this.realTeamService.findAllRealTeams();
        List<Player> players = this.playerService.findAllPlayers();
        modelAndView.addObject("realTeams", realTeams);
        modelAndView.addObject("realPlayers", players);

        return modelAndView;
    }

}
