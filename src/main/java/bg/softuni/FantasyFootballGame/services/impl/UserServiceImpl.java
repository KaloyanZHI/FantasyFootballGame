package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.dto.UserRegisterDTO;
import bg.softuni.FantasyFootballGame.entities.*;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.RoleRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.UserService;
import bg.softuni.FantasyFootballGame.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        if (optUser.isPresent()) {
            return false;
        }
        User newUser = new User();

        newUser.setUsername(dto.getUsername());
        newUser.setRoles(List.of(roleRepository.findByName(UserRolesEnum.USER)));
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
        List<Role> rolesList = new ArrayList<>();
        rolesList.add(roleRepository.findByName(UserRolesEnum.ADMIN));
        rolesList.add(roleRepository.findByName(UserRolesEnum.USER));
        admin.setRoles(rolesList);
        this.userRepository.save(admin);

    }

    @Override
    public boolean passwordMatches(UserRegisterDTO user) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

    @Override
    public boolean checkIfUserWithSameUsernameExists(UserRegisterDTO userRegisterDTO) {
        return this.userRepository.findByUsername(userRegisterDTO.getUsername()).isPresent();
    }

    @Override
    public boolean checkIfUserWithSameEmailExists(UserRegisterDTO userRegisterDTO) {
        return this.userRepository.findByEmail(userRegisterDTO.getEmail()).isPresent();
    }

    @Override
    public boolean checkIfUserWithSameTeamNameExists(UserRegisterDTO userRegisterDTO) {
        System.out.println(this.fantasyTeamRepository.findByTeamName(userRegisterDTO.getFantasyTeam()).isPresent());
        return this.fantasyTeamRepository.findByTeamName(userRegisterDTO.getFantasyTeam()).isPresent();

    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist! Username: " + username));
    }

    @Override
    public FantasyTeam findUserFantasyTeam(Principal principal) {
        User user = findByUsername(principal.getName());
        return user.getFantasyTeam();
    }

    @Override
    public Double findUserBudget(Principal principal) {
        User user = findByUsername(principal.getName());
        return user.getBudget();
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!", id));
        FantasyTeam fantasyTeam = this.fantasyTeamRepository.findById(user.getFantasyTeam().getId())
                .orElseThrow(() -> new ObjectNotFoundException("Team not found!", id));


        this.userRepository.delete(user);
        this.fantasyTeamRepository.delete(fantasyTeam);
    }

    @Override
    public void addRoles(Map<User, List<Role>> userRoleMap, Long id) {
        Role adminRole = this.roleRepository.findByName(UserRolesEnum.ADMIN);
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found", id));
        for (Map.Entry<User, List<Role>> entry : userRoleMap.entrySet()) {
            if (entry.getKey() == user) {
                if (entry.getValue().size() > 1) {
                    System.out.println("Already an admin!");
                }
                entry.getValue().add(adminRole);
                this.userRepository.save(user);
            }
        }


    }

    @Override
    public void removeRoles(Map<User, List<Role>> userRoleMap, Long userId) {
        Role adminRole = this.roleRepository.findByName(UserRolesEnum.ADMIN);
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not found", userId));
        for (Map.Entry<User, List<Role>> entry : userRoleMap.entrySet()) {
            if (entry.getKey() == user) {
                entry.getValue().remove(adminRole);
                this.userRepository.save(user);
            }
        }


    }


}
