package bg.softuni.FantasyFootballGame.services.implementations;

import bg.softuni.FantasyFootballGame.dto.UserLoginDTO;
import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final FantasyTeamService fantasyTeamService;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, FantasyTeamService fantasyTeamService) {

        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.fantasyTeamService = fantasyTeamService;
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


}
