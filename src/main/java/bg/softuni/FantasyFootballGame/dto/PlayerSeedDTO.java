package bg.softuni.FantasyFootballGame.dto;

public class PlayerSeedDTO {
    private String firstName;

    private String lastName;

    private Long realTeam;

    private String playerPosition;

    private Double price;

    public PlayerSeedDTO(){}

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

    public Long getRealTeam() {
        return realTeam;
    }

    public void setRealTeam(Long realTeam) {
        this.realTeam = realTeam;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
