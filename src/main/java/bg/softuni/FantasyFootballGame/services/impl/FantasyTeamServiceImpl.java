package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import org.springframework.stereotype.Service;

@Service
public class FantasyTeamServiceImpl implements FantasyTeamService {
   private final FantasyTeamRepository fantasyTeamRepository;

    public FantasyTeamServiceImpl(FantasyTeamRepository fantasyTeamRepository) {
        this.fantasyTeamRepository = fantasyTeamRepository;
    }

    @Override
    public FantasyTeam createFantasyTeam(String teamName) {
        FantasyTeam fantasyTeam = new FantasyTeam();
        fantasyTeam.setTeamName(teamName);
        fantasyTeamRepository.save(fantasyTeam);
        return fantasyTeam;

    }


}
