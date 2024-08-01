package bg.softuni.FantasyFootballGame.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fantasy_teams")
public class FantasyTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "team_name", unique = true)
    private String teamName;

    @ManyToMany
    private List<Player> players;
    @Column(name = "total_team_points")
    private Double totalTeamPoints;

    public Double getTotalTeamPoints() {
        return totalTeamPoints;
    }

    public void setTotalTeamPoints(Double totalTeamPoints) {
        this.totalTeamPoints = totalTeamPoints;

    }

    public FantasyTeam() {
        this.players = new ArrayList<>();
        this.totalTeamPoints = 0.0;
    }

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}
