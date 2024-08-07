package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.config.WebConfig;
import bg.softuni.FantasyFootballGame.config.I18NConfig;
import bg.softuni.FantasyFootballGame.config.TestSecurityConfig;
import bg.softuni.FantasyFootballGame.repositories.RealTeamRepository;
import bg.softuni.FantasyFootballGame.services.PlayerService;
import bg.softuni.FantasyFootballGame.services.RealTeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Import({WebConfig.class, I18NConfig.class, TestSecurityConfig.class})
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RealTeamService realTeamService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private RealTeamRepository realTeamRepository;

    @Test
    @WithMockUser
    public void testGetIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser
    public void testGetAbout() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    @WithMockUser
    public void testGetRules() throws Exception {
        mockMvc.perform(get("/rules"))
                .andExpect(status().isOk())
                .andExpect(view().name("rules"));
    }

    @Test
    @WithMockUser
    public void testGoLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isFound()) // 302
                .andExpect(redirectedUrl("/login?logout"));
    }
}
