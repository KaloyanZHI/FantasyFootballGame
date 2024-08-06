package bg.softuni.FantasyFootballGame.service.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Player;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.PlayerRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.impl.FantasyTeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FantasyTeamServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FantasyTeamRepository fantasyTeamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private FantasyTeamServiceImpl fantasyTeamService;

    private Player testPlayer1;
    private Player testPlayer2;
    private User testUser;
    private FantasyTeam testFantasyTeam;

    @BeforeEach
    void setUp() {
        testPlayer1 = new Player();
        testPlayer1.setId(1L);
        testPlayer1.setFirstName("Kaloyan");
        testPlayer1.setLastName("Ivanov");
        testPlayer1.setPrice(10.0);
        testPlayer1.setPoints(5.0);

        testPlayer2 = new Player();
        testPlayer2.setId(2L);
        testPlayer2.setFirstName("Stoyan");
        testPlayer2.setLastName("Stoyanov");
        testPlayer2.setPrice(15.0);
        testPlayer2.setPoints(8.0);

        testFantasyTeam = new FantasyTeam();
        testFantasyTeam.setTeamName("Arsenal");
        testFantasyTeam.setPlayers(new ArrayList<>(List.of(testPlayer1, testPlayer2)));

        testUser = new User();
        testUser.setUsername("testUser");

        testUser.setFantasyTeam(testFantasyTeam);
    }

    @Test
    void testCreateFantasyTeam() {
        when(fantasyTeamRepository.save(any(FantasyTeam.class))).thenReturn(testFantasyTeam);

        FantasyTeam createdTeam = fantasyTeamService.createFantasyTeam("Arsenal");

        Assertions.assertEquals("Arsenal", createdTeam.getTeamName());
        verify(fantasyTeamRepository, times(1)).save(any(FantasyTeam.class));
    }

    @Test
    void testFindAllPlayers() {
        List<Player> players = fantasyTeamService.findAllPlayers(testUser);

        Assertions.assertEquals(2, players.size());
        Assertions.assertEquals("Kaloyan", players.get(0).getFirstName());
        Assertions.assertEquals("Stoyan", players.get(1).getFirstName());
    }

    @Test
    void testAddPlayer() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        Player newPlayer = new Player();
        newPlayer.setId(3L);
        newPlayer.setFirstName("Ivan");
        newPlayer.setLastName("Petrov");
        newPlayer.setPrice(10.0);
        newPlayer.setPoints(7.0);

        when(playerRepository.findById(3L)).thenReturn(Optional.of(newPlayer));

        testFantasyTeam.setPlayers(new ArrayList<>(List.of(testPlayer1)));

        fantasyTeamService.addPlayer(3L, principal);

        Assertions.assertEquals(2, testFantasyTeam.getPlayers().size());
        Assertions.assertEquals(90, testUser.getBudget());
        verify(fantasyTeamRepository, times(1)).save(testFantasyTeam);
    }

    @Test
    void testRemovePlayer() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        testFantasyTeam.setPlayers(new ArrayList<>(List.of(testPlayer1, testPlayer2)));

        fantasyTeamService.removePlayer(1L, principal);

        Assertions.assertEquals(1, testFantasyTeam.getPlayers().size());
        Assertions.assertEquals(110, testUser.getBudget());
        verify(fantasyTeamRepository, times(1)).save(testFantasyTeam);
    }

    @Test
    void testCalculateThisMatchPoints() {
        double points = fantasyTeamService.calculateThisMatchPoints(testFantasyTeam);

        Assertions.assertEquals(13.0, points);
    }

    @Test
    void testResetEverything() {
        List<FantasyTeam> teams = List.of(testFantasyTeam);
        fantasyTeamService.resetEverything(teams);

        Assertions.assertEquals(0.0, testFantasyTeam.getTotalTeamPoints());
        verify(fantasyTeamRepository, times(1)).save(testFantasyTeam);
    }

    @Test
    void testFindAllFantasyTeams() {
        when(fantasyTeamRepository.findAll()).thenReturn(List.of(testFantasyTeam));

        List<FantasyTeam> teams = fantasyTeamService.findAllFantasyTeams();

        Assertions.assertEquals(1, teams.size());
        Assertions.assertEquals("Arsenal", teams.get(0).getTeamName());
    }
}
