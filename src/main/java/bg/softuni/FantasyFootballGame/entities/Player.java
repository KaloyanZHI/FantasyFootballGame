package bg.softuni.FantasyFootballGame.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    private RealTeam realTeam;
    @Enumerated(EnumType.STRING)
    @Column(name = "player_position")
    private PlayerPosition playerPosition;

    private Double points;
    private Double price;

    private Double matchRating;

    public Double getMatchRating() {
        return matchRating;
    }

    public void setMatchRating(Double matchRating) {
        this.matchRating = matchRating;
    }

    public Player(){

        this.matchRating = 0.0;
        this.points = 0.0;
    }



    public PlayerPosition getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(PlayerPosition playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RealTeam getRealTeam() {
        return realTeam;
    }

    public void setRealTeam(RealTeam realTeam) {
        this.realTeam = realTeam;
    }
}
