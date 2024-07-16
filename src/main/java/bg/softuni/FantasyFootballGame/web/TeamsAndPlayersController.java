package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamsAndPlayersController {




    private final RealTeamService realTeamService;

    private final PlayerService playerService;

    public TeamsAndPlayersController(RealTeamRepository realTeamRepository, PlayerRepository playerRepository, RealTeamService realTeamService, PlayerService playerService) {

        this.realTeamService = realTeamService;
        this.playerService = playerService;
    }

    @GetMapping("/teams-and-players")
    public ModelAndView getTeamAndPlayers() throws IOException {
        List<RealTeam> realTeams = this.realTeamService.findAllRealTeams();
        List<Player> players = this.playerService.findAllPlayers();
       ModelAndView modelAndView = new ModelAndView("teams-and-players");
       modelAndView.addObject("teams", realTeams);
       modelAndView.addObject("players", players);



        return modelAndView;
    }

}
