package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.entities.Player;

import java.io.IOException;
import java.util.List;

public interface RealTeamService {
     void seedRealTeams() throws IOException;

     String readTeamFromFile() throws IOException;


}
