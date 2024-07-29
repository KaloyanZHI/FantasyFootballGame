package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FantasyTeamServiceImpl implements FantasyTeamService {
   private final FantasyTeamRepository fantasyTeamRepository;

   private final UserService userService;

   private final PlayerService playerService;



    public FantasyTeamServiceImpl(FantasyTeamRepository fantasyTeamRepository, @Lazy UserService userService, PlayerService playerService) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.userService = userService;
        this.playerService = playerService;
    }

    @Override
    public FantasyTeam createFantasyTeam(String teamName) {
        FantasyTeam fantasyTeam = new FantasyTeam();
        fantasyTeam.setTeamName(teamName);
        fantasyTeamRepository.save(fantasyTeam);
        return fantasyTeam;

    }



    @Override
    public List<Player> findAllPlayers(User user) {
        return user.getFantasyTeam().getPlayers();
    }

    @Override
    public void addPlayer(Long playerId, Principal principal) {
        Player player = this.playerService.findPlayerById(playerId);
        User user = this.userService.findByUsername(principal.getName());

        FantasyTeam fantasyTeam = user.getFantasyTeam();
        if (player != null){
            if (fantasyTeam.getPlayers().size() == 11){
                throw new ArithmeticException ("Maximum 11 players are allowed!");
            }
            if (user.getBudget() < player.getPrice()){
                throw new IllegalArgumentException("Not enough budget!");
            }
            fantasyTeam.getPlayers().add(player);
            user.setBudget(user.getBudget() - player.getPrice());
            fantasyTeamRepository.save(fantasyTeam);
        }
        else {
            throw new IllegalArgumentException("No player found!");
        }


    }


}
