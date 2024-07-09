package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
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
    private final RealTeamRepository realTeamRepository;

    private final PlayerRepository playerRepository;

    private final RealTeamService realTeamService;

    public TeamsAndPlayersController(RealTeamRepository realTeamRepository, PlayerRepository playerRepository, RealTeamService realTeamService) {
        this.realTeamRepository = realTeamRepository;
        this.playerRepository = playerRepository;
        this.realTeamService = realTeamService;
    }

    @GetMapping("/teams-and-players")
    public ModelAndView getTeamAndPlayers() throws IOException {
        List<RealTeam> realTeams = new ArrayList<>(this.realTeamRepository.findAll());
        List<Player> players = new ArrayList<>(this.playerRepository.findAll());
       ModelAndView modelAndView = new ModelAndView("teams-and-players");
       modelAndView.addObject("teams", realTeams);
       modelAndView.addObject("players", players);



        return modelAndView;
    }

}
