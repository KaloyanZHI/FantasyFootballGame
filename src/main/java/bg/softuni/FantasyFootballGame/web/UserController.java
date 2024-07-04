package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @ModelAttribute("registerData")
    public UserRegisterDTO registerDTO(){
        return new UserRegisterDTO();
    };
    @ModelAttribute("loginData")
    public UserLoginDTO loginDTO(){
        return new UserLoginDTO();
    };

    @GetMapping("/register")
    public String register(){

        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegisterDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){

        if (bindingResult.hasErrors() || !data.getPassword().equals(data.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/register";

        }
        boolean successfulRegistration = userService.register(data);

        if (!successfulRegistration){
            return "redirect:/register";
        }



        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @Valid UserLoginDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes

    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
