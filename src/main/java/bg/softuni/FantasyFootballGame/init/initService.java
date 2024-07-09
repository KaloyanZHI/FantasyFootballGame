package bg.softuni.FantasyFootballGame.init;

import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import bg.softuni.FantasyFootballGame.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initService implements CommandLineRunner {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    private final RoleService roleService;

    public initService(RealTeamService realTeamService, PlayerService playerService, RoleService roleService) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.realTeamService.seedRealTeams();
        this.playerService.seedPlayers();
        this.roleService.seedRoles();

    }
}
