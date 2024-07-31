package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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

    List<User> findAllUsers();


    void deleteUser(Long id);
    void addRoles(Map<User, List<Role>> userRoleMap, Long id);

    void removeRoles(Map<User, List<Role>> userRoleMap, Long userId);
}



