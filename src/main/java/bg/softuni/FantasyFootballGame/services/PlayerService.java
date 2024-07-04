package bg.softuni.FantasyFootballGame.services;

import java.io.IOException;

public interface PlayerService {
    void seedPlayers() throws IOException;

    String readPlayersFromFile() throws IOException;
}
