package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.dto.PlayerSeedDTO;
import bg.softuni.FantasyFootballGame.entities.RealTeam;
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
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final String PLAYER_FILE_PATH = "src/main/resources/jsonData/playersData.json";
    private final PlayerRepository playerRepository;

    private final RealTeamRepository realTeamRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    public PlayerServiceImpl(PlayerRepository playerRepository, RealTeamRepository realTeamRepository, ModelMapper modelMapper, Gson gson) {
        this.playerRepository = playerRepository;
        this.realTeamRepository = realTeamRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public String readPlayersFromFile() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public List<Player> findAllPlayers() {
        return this.playerRepository.findAll();
    }

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
