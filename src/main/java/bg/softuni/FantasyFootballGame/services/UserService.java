package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;

import java.security.Principal;

public interface UserService {
    public boolean register(UserRegisterDTO data);

     void seedUsers();

    boolean passwordMatches(UserRegisterDTO user);

    boolean checkIfUserWithSameUsernameExists(UserRegisterDTO userRegisterDTO);

    boolean checkIfUserWithSameEmailExists(UserRegisterDTO userRegisterDTO);

    boolean checkIfUserWithSameTeamNameExists(UserRegisterDTO userRegisterDTO);

    User findByUsername(String username);
    FantasyTeam findUserFantasyTeam(Principal principal);

    Double findUserBudget(Principal principal);



}



