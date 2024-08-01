package bg.softuni.FantasyFootballGame.services;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;

import java.security.Principal;
import java.util.List;

public interface FantasyTeamService {
   public FantasyTeam createFantasyTeam(String teamName);



   List<Player> findAllPlayers(User user);

   void addPlayer(Long playerId, Principal principal);


   void removePlayer(Long playerId, Principal principal);

   Double calculateThisMatchPoints(FantasyTeam fantasyTeam);

   void resetEverything(List<FantasyTeam> fantasyTeams);

   List<FantasyTeam> findAllFantasyTeams();
}
