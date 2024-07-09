package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    public HomeController(RealTeamRepository realTeamRepository, RealTeamService realTeamService, PlayerService playerService) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String getIndex() throws IOException {

        return "index";
    }
    @GetMapping("/home")
    public String getHome() throws IOException {

        return "home";
    }

    @GetMapping("/news")
    public String getNews() throws IOException {

        return "news";
    }
    @GetMapping("/about")
    public String getAbout() throws IOException {

        return "about";
    }
    @GetMapping("/rules")
    public String getRules() throws IOException {

        return "rules";
    }

    @GetMapping("/my-team")
    public String goMyTeam() {

        return "my-team";
    }
    @GetMapping("/admin-tools")
    public String goAdminTools() {

        return "admin-tools";
    }
    @GetMapping("/logout")
    public String goLogout() {

        return "logout";
    }





}
