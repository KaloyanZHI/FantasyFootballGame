package bg.softuni.FantasyFootballGame.services;

import java.io.IOException;

public interface RealTeamService {
     void seedRealTeams() throws IOException;

     String readTeamFromFile() throws IOException;
}
