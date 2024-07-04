package bg.softuni.FantasyFootballGame.dto;

import bg.softuni.FantasyFootballGame.entities.FantasyTeam;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotNull
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    @Size(min = 3, max = 20)
    private String password;
    @NotNull
    @Size(min = 3, max = 20)
    private String confirmPassword;
    @NotNull
    @Size(min = 3, max = 20)
    private String fantasyTeam;
    @Positive
    private int age;
    @Email
    private String email;

    public UserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(String fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
