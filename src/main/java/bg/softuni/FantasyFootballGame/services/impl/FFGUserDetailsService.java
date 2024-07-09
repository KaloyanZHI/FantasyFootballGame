package bg.softuni.FantasyFootballGame.services.impl;

import bg.softuni.FantasyFootballGame.entities.Role;
import bg.softuni.FantasyFootballGame.entities.User;
import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import bg.softuni.FantasyFootballGame.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FFGUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public FFGUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(FFGUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

    }

    private static UserDetails map(User user) {
        return org.springframework.security.core.userdetails
                .User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(FFGUserDetailsService::grantAuthority)
                        .toList())
                .disabled(false)
                .build();
    }
    private static GrantedAuthority grantAuthority(Role role){
return new SimpleGrantedAuthority("ROLE_" + role.getName());


    }
}
