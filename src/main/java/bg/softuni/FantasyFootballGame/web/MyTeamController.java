package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my-team")
public class MyTeamController {
    private final FantasyTeamService fantasyTeamService;

    private final UserService userService;


    public MyTeamController(FantasyTeamService fantasyTeamService, UserService userService) {
        this.fantasyTeamService = fantasyTeamService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView myTeam(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("my-team");
        FantasyTeam currentFantasyTeam = this.userService.findUserFantasyTeam(principal);
        Double thisMatchPoints = this.fantasyTeamService.calculateThisMatchPoints(currentFantasyTeam);
        modelAndView.addObject("userFantasyTeam", currentFantasyTeam);
        modelAndView.addObject("thisMatchPoints",thisMatchPoints);

        return modelAndView;
    }

    @RequestMapping("/add/{id}")
    public ModelAndView addPlayer(@PathVariable("id") Long id,
                                  Principal principal,
                                  HttpServletRequest httpRequest) {
        User currentUser = this.userService.findByUsername(principal.getName());
        FantasyTeam currentFantasyTeam = currentUser.getFantasyTeam();
        this.fantasyTeamService.addPlayer(id, principal);
        String referer = httpRequest.getHeader("Referer");
        ModelAndView modelAndView = new ModelAndView("redirect:" + referer);
        modelAndView.addObject("fantasyTeam", currentFantasyTeam);
        modelAndView.addObject("user", currentUser);
        return modelAndView;

    }

    @RequestMapping("/remove/{id}")
    public ModelAndView removePlayer(@PathVariable("id") Long playerId,
                                     Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        FantasyTeam fantasyTeam = user.getFantasyTeam();


        fantasyTeamService.removePlayer(playerId, principal);


        return new ModelAndView("redirect:/my-team");
    }


}
