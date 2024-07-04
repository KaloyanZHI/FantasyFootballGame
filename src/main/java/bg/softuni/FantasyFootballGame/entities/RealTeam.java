package bg.softuni.FantasyFootballGame.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "real_teams")
public class RealTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_name")
    private String teamName;
    @OneToMany(targetEntity = Player.class, mappedBy = "realTeam")
    private Set<Player> players;

    public RealTeam(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

}
