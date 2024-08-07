package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.config.I18NConfig;
import bg.softuni.FantasyFootballGame.config.TestSecurityConfig;
import bg.softuni.FantasyFootballGame.config.WebConfig;
import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.services.FantasyTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = RankingsController.class)
@Import({WebConfig.class, I18NConfig.class, TestSecurityConfig.class})
class RankingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FantasyTeamService fantasyTeamService;

    @Test
    public void testGetRankings() throws Exception {

        FantasyTeam team1 = new FantasyTeam();
        team1.setTotalTeamPoints(100.0);
        FantasyTeam team2 = new FantasyTeam();
        team2.setTotalTeamPoints(200.0);
        FantasyTeam team3 = new FantasyTeam();
        team3.setTotalTeamPoints(150.0);

        List<FantasyTeam> teams = Arrays.asList(team1, team2, team3);
        when(fantasyTeamService.findAllFantasyTeams()).thenReturn(teams);


        ResultActions resultActions = mockMvc.perform(get("/rankings"));


        resultActions.andExpect(status().isOk())
                .andExpect(view().name("rankings"))
                .andExpect(model().attributeExists("rankedTeams"))
                .andExpect(model().attribute("rankedTeams", Arrays.asList(team2, team3, team1)));
    }
}
