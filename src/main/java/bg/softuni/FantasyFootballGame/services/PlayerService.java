package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.entities.Player;

import java.io.IOException;
import java.util.List;

public interface PlayerService {
    void seedPlayers() throws IOException;

    String readPlayersFromFile() throws IOException;

    List<Player> findAllPlayers();

    Player findPlayerById(Long id);

    void scoreGoal(Player player);

    void updatePlayerTotalPoints(Player player, Double matchRating);

    void prepareForNextMatch();
}
