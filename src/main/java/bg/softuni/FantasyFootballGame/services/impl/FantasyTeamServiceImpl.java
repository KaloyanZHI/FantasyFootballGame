package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.UserService;
import bg.softuni.FantasyFootballGame.services.exceptions.BudgetExceededException;
import bg.softuni.FantasyFootballGame.services.exceptions.MaximumPlayersExceededException;
import bg.softuni.FantasyFootballGame.services.exceptions.PlayerAlreadyExistsException;
import bg.softuni.FantasyFootballGame.services.exceptions.PlayerNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

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
        if (fantasyTeam.getPlayers().contains(player)){
            throw new PlayerAlreadyExistsException("Player already exists!", player.getFirstName(), player.getLastName());
        }
        if (player != null){
            if (fantasyTeam.getPlayers().size() == 11){
                throw new MaximumPlayersExceededException("Maximum 11 players are allowed!");
            }
            if (user.getBudget() < player.getPrice()){
                throw new BudgetExceededException("Not enough budget!");
            }
            fantasyTeam.getPlayers().add(player);
            user.setBudget(user.getBudget() - player.getPrice());
            fantasyTeamRepository.save(fantasyTeam);
        }
        else {
            throw new PlayerNotFoundException("No player found!");
        }


    }

    @Override
    public void removePlayer(Long playerId, Principal principal) {
        User user = this.userService.findByUsername(principal.getName());
        FantasyTeam fantasyTeam = user.getFantasyTeam();
        List<Player> playersList = fantasyTeam.getPlayers();
        Player playerById = new Player();
        for (Player player : playersList) {
            if (player.getId().equals(playerId)){
                playerById = player;

                break;
            }
        }

            playersList.remove(playerById);
            user.setBudget(user.getBudget() + playerById.getPrice());
            fantasyTeamRepository.save(fantasyTeam);


    }


}
