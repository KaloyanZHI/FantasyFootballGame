package bg.softuni.FantasyFootballGame.config;

import bg.softuni.FantasyFootballGame.entities.UserRolesEnum;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/",
                                        "/about",
                                        "/news/all",
                                        "/rules",
                                        "/teams-and-players",
                                        "successful-register",
                                        "news/**",
                                        "/login-error",
                                        "/error",
                                        "/rankings")
                                .permitAll()
                                .requestMatchers("/login", "/register").anonymous()
                                .requestMatchers("/admin-tools",
                                        "/add-points",
                                        "/news/delete/",
                                        "news/write-news",
                                        "add-points/reset-everything",
                                        "add-points/reset-ratings",
                                        "add-points/score-goal/",
                                        "admin-tools/delete/",
                                        "admin-tools/remove-role/").hasRole(UserRolesEnum.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureForwardUrl("/login-error")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                )
                .build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
