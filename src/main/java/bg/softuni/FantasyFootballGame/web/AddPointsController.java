package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("add-points")
public class AddPointsController {
    private final PlayerService playerService;

    private final FantasyTeamService fantasyTeamService;

    public AddPointsController(PlayerService playerService, FantasyTeamService fantasyTeamService) {
        this.playerService = playerService;
        this.fantasyTeamService = fantasyTeamService;
    }

    @GetMapping
    public ModelAndView addPoints() {
        ModelAndView modelAndView = new ModelAndView("add-points");
        modelAndView.addObject("allPlayers", this.playerService.findAllPlayers());
        return modelAndView;
    }

    @RequestMapping("/score-goal/{id}")
    public ModelAndView scoreAGoal(@PathVariable("id") Long playerId) {

        this.playerService.scoreGoal(this.playerService.findPlayerById(playerId));

        return new ModelAndView("redirect:/add-points");

    }

    @ModelAttribute("player")
    public Player player() {
        return new Player();
    }

    @PostMapping(value = "/match-rating/{id}")
    public ModelAndView addMatchRating(@PathVariable("id") Long playerId,
                                       @RequestParam("matchRating") Double mathRating) {
        Player player = this.playerService.findPlayerById(playerId);
        this.playerService.updatePlayerTotalPoints(player, mathRating);



        return new ModelAndView("redirect:/add-points");

    }

    @RequestMapping("/reset-ratings")
    public ModelAndView resetMatchRating() {

        this.playerService.prepareForNextMatch();

        return new ModelAndView("redirect:/add-points");

    }
    @RequestMapping("/reset-everything")
    public ModelAndView resetEverything() {

        this.playerService.prepareForNextMatch();

        this.fantasyTeamService.resetEverything(this.fantasyTeamService.findAllFantasyTeams());


        return new ModelAndView("redirect:/add-points");

    }

}
