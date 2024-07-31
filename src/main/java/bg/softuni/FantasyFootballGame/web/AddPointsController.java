package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.services.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("add-points")
public class AddPointsController {
    private final PlayerService playerService;

    public AddPointsController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ModelAndView addPoints(){
        ModelAndView modelAndView = new ModelAndView("add-points");
        modelAndView.addObject("allPlayers", this.playerService.findAllPlayers());
        return modelAndView;
    }

}
