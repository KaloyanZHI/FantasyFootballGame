package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin-tools")
public class AdminToolsController {
    private final UserService userService;

    public AdminToolsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView goAdminTools() {
        ModelAndView modelAndView = new ModelAndView("admin-tools");
        List<User> userList = this.userService.findAllUsers();


        modelAndView.addObject("allUsers", userList);


        return modelAndView;
    }
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return new ModelAndView("redirect:/admin-tools");
    }
    @RequestMapping("add-role/{id}")
    public ModelAndView addRole(@PathVariable("id") Long userId){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin-tools");
        List<User> userList = this.userService.findAllUsers();
        Map<User, List<Role>> userRoleMap = new HashMap<>();
        for (User user : userList) {
            userRoleMap.put(user, user.getRoles());
        }
        this.userService.addRoles(userRoleMap, userId);

        return modelAndView;
    }
    @RequestMapping("remove-role/{id}")
    public ModelAndView removeRole(@PathVariable("id") Long userId){
        List<User> userList = this.userService.findAllUsers();
        Map<User, List<Role>> userRoleMap = new HashMap<>();
        for (User user : userList) {
            userRoleMap.put(user, user.getRoles());
        }
        this.userService.removeRoles(userRoleMap, userId);

        return new ModelAndView("redirect:/admin-tools");
    }

}

