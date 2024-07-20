package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.News;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.RoleRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final FantasyTeamService fantasyTeamService;

    private final FantasyTeamRepository fantasyTeamRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, FantasyTeamService fantasyTeamService, FantasyTeamRepository fantasyTeamRepository, RoleRepository roleRepository) {

        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.fantasyTeamService = fantasyTeamService;
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean register(UserRegisterDTO dto) {

        Optional<User> optUser = userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getEmail());
        if (optUser.isPresent()){
            return false;
        }
        User newUser = new User();

        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        newUser.setAge(dto.getAge());
        newUser.setFantasyTeam(this.fantasyTeamService.createFantasyTeam(dto.getFantasyTeam()));


        this.userRepository.save(newUser);

        return true;


    }

    @Override
    public void seedUsers() {
        User user = new User();
        FantasyTeam userFantasyTeam = new FantasyTeam();
        userFantasyTeam.setTeamName("UserFC");
        this.fantasyTeamRepository.save(userFantasyTeam);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("asdasd"));
        user.setAge(34);
        user.setFantasyTeam(userFantasyTeam);
        user.setEmail("user@user");
        user.setRoles(List.of(roleRepository.findByName(UserRolesEnum.USER)));
        this.userRepository.save(user);

        User admin = new User();
        FantasyTeam adminFantasyTeam = new FantasyTeam();
        adminFantasyTeam.setTeamName("AdminFC");
        this.fantasyTeamRepository.save(adminFantasyTeam);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("asdasd"));
        admin.setAge(34);
        admin.setFantasyTeam(adminFantasyTeam);
        admin.setEmail("admin@user");
        admin.setRoles(List.of(roleRepository.findByName(UserRolesEnum.USER)));
        admin.setRoles(List.of(roleRepository.findByName(UserRolesEnum.ADMIN)));
        this.userRepository.save(admin);

    }

    @Override
    public boolean passwordMatches(UserRegisterDTO user) {
        return user.getPassword().equals(user.getConfirmPassword());
    }


}
