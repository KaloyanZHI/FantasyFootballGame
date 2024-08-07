package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.UserService;
import bg.softuni.FantasyFootballGame.services.exceptions.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * A service that manages The Fantasy Teams
 */
@Service
public class FantasyTeamServiceImpl implements FantasyTeamService {
    private final FantasyTeamRepository fantasyTeamRepository;


    private final UserRepository userRepository;


    private final PlayerRepository playerRepository;


    public FantasyTeamServiceImpl(FantasyTeamRepository fantasyTeamRepository, UserRepository userRepository, PlayerRepository playerRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
    }

    /**
     * Method that creates a new Fantasy Team
     *
     * @param teamName the name of the new team
     * @return fantasyTeam
     */
    @Override
    public FantasyTeam createFantasyTeam(String teamName) {
        FantasyTeam fantasyTeam = new FantasyTeam();
        fantasyTeam.setTeamName(teamName);
        fantasyTeamRepository.save(fantasyTeam);
        return fantasyTeam;

    }

    /**
     * @param user Method that finds and returns the players in the user's Fantasy team
     * @return List of players in the user's Fantasy team
     */

    @Override
    public List<Player> findAllPlayers(User user) {
        return user.getFantasyTeam().getPlayers();
    }

    /**
     * Method that adds a player to a logged User's team
     *
     * @param playerId the id of the player to add
     * @param principal the logged user
     */
    @Override
    public void addPlayer(Long playerId, Principal principal) {
        Player player = this.playerRepository.findById(playerId)
                .orElseThrow(() -> new ObjectNotFoundException("Player not found", playerId));
        User user = this.userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ObjectNotFoundException("User not found", playerId));

        FantasyTeam fantasyTeam = user.getFantasyTeam();
        if (fantasyTeam.getPlayers().contains(player)) {
            throw new PlayerAlreadyExistsException("Player already exists!", player.getFirstName(), player.getLastName());
        }
        if (fantasyTeam.getPlayers().size() == 11) {
            throw new MaximumPlayersExceededException("Maximum 11 players are allowed!");
        }
        if (user.getBudget() < player.getPrice()) {
            throw new BudgetExceededException("Not enough budget!");
        }
        fantasyTeam.getPlayers().add(player);
        user.setBudget(user.getBudget() - player.getPrice());
        fantasyTeamRepository.save(fantasyTeam);


    }

    /**
     * Method that removes a player from a logged User's team
     *
     * @param playerId the id of the player to remove
     * @param principal the logged user
     */

    @Override
    public void removePlayer(Long playerId, Principal principal) {
        User user = this.userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ObjectNotFoundException("User not found", playerId));
        FantasyTeam fantasyTeam = user.getFantasyTeam();
        List<Player> playersList = fantasyTeam.getPlayers();
        Player playerById = playersList.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Player not found", playerId));
        playersList.remove(playerById);
        user.setBudget(user.getBudget() + playerById.getPrice());
        fantasyTeamRepository.save(fantasyTeam);


    }

    /**
     * Method that calculates calculates the points of all players in a team
     * @param fantasyTeam the team which points are calculated
     * @return total points
     */
    @Override
    public Double calculateThisMatchPoints(FantasyTeam fantasyTeam) {
        double totalTeamPoints = 0;
        List<Player> players = fantasyTeam.getPlayers();
        for (Player player : players) {
            totalTeamPoints += player.getPoints();
        }

        return totalTeamPoints;
    }

    /**
     * Method that resets the points of all fantasy teams to 0.
     * Used at the start of the season so every team stars with 0 points
     * @param fantasyTeams all fantasy teams
     */

    @Override
    public void resetEverything(List<FantasyTeam> fantasyTeams) {
        for (FantasyTeam fantasyTeam : fantasyTeams) {
            fantasyTeam.setTotalTeamPoints(0.0);
            this.fantasyTeamRepository.save(fantasyTeam);
        }
    }

    /**
     *
     * Method that returns all fantasy teams
     */
    @Override
    public List<FantasyTeam> findAllFantasyTeams() {
        return this.fantasyTeamRepository.findAll();
    }


}
