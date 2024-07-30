package bg.softuni.FantasyFootballGame.services.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
