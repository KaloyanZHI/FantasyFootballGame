package bg.softuni.FantasyFootballGame.init;

import bg.softuni.FantasyFootballGame.repositories.NewsRepository;
import bg.softuni.FantasyFootballGame.repositories.RoleRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initService implements CommandLineRunner {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    private final RoleService roleService;

    private final RoleRepository roleRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final NewsService newsService;

    private final NewsRepository newsRepository;

    public initService(RealTeamService realTeamService, PlayerService playerService, RoleService roleService, RoleRepository roleRepository, UserService userService, UserRepository userRepository, NewsService newsService, NewsRepository newsRepository) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.newsService = newsService;
        this.newsRepository = newsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.realTeamService.seedRealTeams();
        this.playerService.seedPlayers();
        if (this.roleRepository.count() == 0) {
            this.roleService.seedRoles();
        }
        if (this.userRepository.count() == 0){
            this.userService.seedUsers();
        }
        if (this.newsRepository.count() == 0){
            this.newsService.seedNews();
        }

    }
}
