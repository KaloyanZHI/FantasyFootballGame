package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminToolsController {
    private final UserService userService;

    public AdminToolsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin-tools")
    public ModelAndView goAdminTools() {
        ModelAndView modelAndView = new ModelAndView("admin-tools");
        List<User> userList = this.userService.findAllUsers();
        modelAndView.addObject("allUsers", userList);

        return modelAndView;
    }
}
