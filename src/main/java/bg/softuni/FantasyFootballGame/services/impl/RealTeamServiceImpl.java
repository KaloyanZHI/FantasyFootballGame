package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class RealTeamServiceImpl implements RealTeamService {
    private static final String REAL_TEAMS_FILE_PATH = "src/main/resources/jsonData/realTeams.json";
    private final RealTeamRepository realTeamRepository;
    private final Gson gson;

    public RealTeamServiceImpl(RealTeamRepository realTeamRepository, Gson gson) {
        this.realTeamRepository = realTeamRepository;
        this.gson = gson;
    }

    @Override
    public String readTeamFromFile() throws IOException {
        return Files.readString(Path.of(REAL_TEAMS_FILE_PATH));


    }

    @Override
    public void seedRealTeams() throws IOException {
        RealTeam[] realTeams = this.gson.fromJson(readTeamFromFile(), RealTeam[].class);
        for (RealTeam realTeam : realTeams) {
            Optional<RealTeam> optRealTeam = this.realTeamRepository.findByTeamName(realTeam.getTeamName());
            if (optRealTeam.isEmpty()) {
                RealTeam newRealTeam = new RealTeam();
                newRealTeam.setTeamName(realTeam.getTeamName());
                newRealTeam.setImageUrl(realTeam.getImageUrl());
                this.realTeamRepository.save(newRealTeam);
            }
        }


    }

}
