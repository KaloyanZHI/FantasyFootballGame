package bg.softuni.FantasyFootballGame.web;

import bg.softuni.FantasyFootballGame.config.I18NConfig;
import bg.softuni.FantasyFootballGame.config.TestSecurityConfig;
import bg.softuni.FantasyFootballGame.config.WebConfig;
import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import bg.softuni.FantasyFootballGame.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminToolsController.class)
@Import({WebConfig.class, I18NConfig.class, TestSecurityConfig.class})
public class AdminToolsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGoAdminTools() throws Exception {
        // Arrange
        FantasyTeam team1 = new FantasyTeam();
        team1.setTeamName("Team1");

        FantasyTeam team2 = new FantasyTeam();
        team2.setTeamName("Team2");

        User user1 = new User();
        user1.setUsername("user1");
        user1.setFantasyTeam(team1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setFantasyTeam(team2);

        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1, user2));
        when(userService.findByUsername(anyString())).thenReturn(user1);

        // Act & Assert
        mockMvc.perform(get("/admin-tools"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-tools"))
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(model().attributeExists("currentUserName"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        // Act
        mockMvc.perform(delete("/admin-tools/delete/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-tools"));

        // Assert
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddRole() throws Exception {
        // Arrange
        FantasyTeam team = new FantasyTeam();
        team.setTeamName("Team1");

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setFantasyTeam(team);

        Role userRole = new Role();
        userRole.setName(UserRolesEnum.USER);
        user1.setRoles(Arrays.asList(userRole));

        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1));

        // Act
        mockMvc.perform(post("/admin-tools/add-role/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-tools"));

        // Assert
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userService, times(1)).addRoles(any(Map.class), userIdCaptor.capture());
        assert userIdCaptor.getValue().equals(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testRemoveRole() throws Exception {
        // Arrange
        FantasyTeam team = new FantasyTeam();
        team.setTeamName("Team1");

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setFantasyTeam(team);

        Role adminRole = new Role();
        adminRole.setName(UserRolesEnum.ADMIN);
        Role userRole = new Role();
        userRole.setName(UserRolesEnum.USER);
        user1.setRoles(Arrays.asList(adminRole, userRole));

        when(userService.findAllUsers()).thenReturn(Arrays.asList(user1));

        // Act
        mockMvc.perform(post("/admin-tools/remove-role/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin-tools"));

        // Assert
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userService, times(1)).removeRoles(any(Map.class), userIdCaptor.capture());
        assert userIdCaptor.getValue().equals(1L);
    }
}
