package bg.softuni.FantasyFootballGame.init;

import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initService implements CommandLineRunner {
    private final RealTeamService realTeamService;
    private final PlayerService playerService;

    public initService(RealTeamService realTeamService, PlayerService playerService) {
        this.realTeamService = realTeamService;
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.realTeamService.seedRealTeams();
        this.playerService.seedPlayers();

    }
}
