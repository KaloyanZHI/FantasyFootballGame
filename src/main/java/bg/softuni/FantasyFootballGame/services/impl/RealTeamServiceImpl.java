package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A service that manages the real teams
 */
@Service
public class RealTeamServiceImpl implements RealTeamService {
    private static final String REAL_TEAMS_FILE_PATH = "src/main/resources/jsonData/realTeams.json";
    private final RealTeamRepository realTeamRepository;
    private final Gson gson;

    private final PlayerRepository playerRepository;

    public RealTeamServiceImpl(RealTeamRepository realTeamRepository, Gson gson, PlayerRepository playerRepository) {
        this.realTeamRepository = realTeamRepository;
        this.gson = gson;
        this.playerRepository = playerRepository;
    }

    /**
     *
     * Method that reads the real teams details from a JSON file
     */
    @Override
    public String readTeamFromFile() throws IOException {
        return Files.readString(Path.of(REAL_TEAMS_FILE_PATH));


    }

    /**
     *
     * Method that returns all the real teams
     */
    @Override
    public List<RealTeam> findAllRealTeams() {
        return this.realTeamRepository.findAll();

    }

    /**
     *
     * Method that seeds the database with some real teams read from a JSON file
     * Made for test purposes
     */

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
