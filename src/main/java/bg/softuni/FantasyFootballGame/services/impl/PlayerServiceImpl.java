package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.dto.PlayerSeedDTO;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A service that manages the players
 */
@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String PLAYER_FILE_PATH = "src/main/resources/jsonData/playersData.json";
    private final PlayerRepository playerRepository;

    private final RealTeamRepository realTeamRepository;

    private final FantasyTeamRepository fantasyTeamRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, RealTeamRepository realTeamRepository, FantasyTeamRepository fantasyTeamRepository, ModelMapper modelMapper, Gson gson) {
        this.playerRepository = playerRepository;
        this.realTeamRepository = realTeamRepository;
        this.fantasyTeamRepository = fantasyTeamRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    /**
     * Method that reads the player from a JSON file
     * @return the players
     * @throws IOException throws exception
     */
    @Override
    public String readPlayersFromFile() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    /**
     * Method that returns all the players
     * @return all players
     */
    @Override
    public List<Player> findAllPlayers() {
        return this.playerRepository.findAll();
    }

    /**
     * Method that searches for a player by an id
     * @param id The id of the searched player
     * @return the player
     */
    @Override
    public Player findPlayerById(Long id) {
        Optional<Player> optPlayer = this.playerRepository.findById(id);
        if(optPlayer.isEmpty()){
            throw new NoSuchElementException("Player not found!");
        }
        return modelMapper.map(optPlayer, Player.class);
    }

    /**
     *
     * Method that automatically gives the player 5 points if he scores a goal
     */
    @Override
    public void scoreGoal(Player player) {
        player.setPoints(player.getPoints() + 5);
        this.playerRepository.save(player);
    }

    /**
     * Method that calculates all the points of the player after the match and updates it
     * @param player the player whose points are updated
     * @param matchRating the match rating added by the admin
     */
    @Override
    public void updatePlayerTotalPoints(Player player, Double matchRating) {
        player.setPoints(player.getPoints() + matchRating);
        player.setMatchRating(matchRating);
        this.playerRepository.save(player);
    }

    /**
     * Method that resets player's points to 0 before the next match starts
     */
    @Override
    public void prepareForNextMatch() {

        List<FantasyTeam> fantasyTeamList = this.fantasyTeamRepository.findAll();
        for (FantasyTeam fantasyTeam : fantasyTeamList) {
            List<Player> players = fantasyTeam.getPlayers();
            Double totalPoints = 0.0;
            for (Player player : players) {
                totalPoints+=player.getPoints();
            }
            fantasyTeam.setTotalTeamPoints(totalPoints + fantasyTeam.getTotalTeamPoints());
            this.fantasyTeamRepository.save(fantasyTeam);
        }
        List<Player> playerList = this.playerRepository.findAll();
        for (Player player : playerList) {
            player.setMatchRating(0.0);
            player.setPoints(0.0);
            this.playerRepository.save(player);
        }

    }

    /**
     *
     * Method that seed the database with players whose details are read from a JSON file
     * Made for test purposes
     */
    @Override
    public void seedPlayers() throws IOException {
        PlayerSeedDTO[] players = gson.fromJson(readPlayersFromFile(), PlayerSeedDTO[].class);
        for (PlayerSeedDTO playerDTO : players) {
            Optional<Player> optPlayer = this.playerRepository.findByFirstNameAndLastName(playerDTO.getFirstName(), playerDTO.getLastName());
            if (optPlayer.isEmpty()){
                Player newPlayer = this.modelMapper.map(playerDTO, Player.class);
                newPlayer.setFirstName(playerDTO.getFirstName());
                newPlayer.setLastName(playerDTO.getLastName());
                Optional<RealTeam> realTeam = this.realTeamRepository.findById(playerDTO.getRealTeam());
                newPlayer.setRealTeam(realTeam.get());
                newPlayer.setPrice(playerDTO.getPrice());
                this.playerRepository.save(newPlayer);
            }

        }

    }

}
