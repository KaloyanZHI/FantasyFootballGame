package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
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

    public TeamsAndPlayersController(RealTeamRepository realTeamRepository, PlayerRepository playerRepository) {
        this.realTeamRepository = realTeamRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/teams-and-players")
    public ModelAndView getTeamAndPlayers() throws IOException {
        List<RealTeam> realTeams = new ArrayList<>(this.realTeamRepository.findAll());
       ModelAndView modelAndView = new ModelAndView("teams-and-players");
       modelAndView.addObject("teams", realTeams);



        return modelAndView;
    }

}
