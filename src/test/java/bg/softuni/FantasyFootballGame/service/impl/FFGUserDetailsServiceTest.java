package bg.softuni.FantasyFootballGame.service.impl;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import bg.softuni.FantasyFootballGame.repositories.FantasyTeamRepository;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import bg.softuni.FantasyFootballGame.services.impl.FFGUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FFGUserDetailsServiceTest {
    private FFGUserDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {


        toTest = new FFGUserDetailsService(mockUserRepository);

    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        User testUser = new User();
        testUser.setUsername("user");
        testUser.setPassword("asdasd");
        testUser.setEmail("user@user");
        testUser.setAge(34);
        testUser.setRoles(List.of(
                new Role().setName(UserRolesEnum.USER),
                new Role().setName(UserRolesEnum.ADMIN)
        ));
        FantasyTeam testTeam = new FantasyTeam();
        testTeam.setTeamName("Arsenal");
        testUser.setFantasyTeam(testTeam);


        when(mockUserRepository.findByUsername("user"))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername("user");

        Assertions.assertInstanceOf(UserDetails.class, userDetails);

        Assertions.assertEquals("user", userDetails.getUsername());
        Assertions.assertEquals("asdasd", userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());


    }
}
