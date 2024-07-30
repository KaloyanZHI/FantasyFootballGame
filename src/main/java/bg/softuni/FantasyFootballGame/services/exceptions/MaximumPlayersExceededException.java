package bg.softuni.FantasyFootballGame.services.exceptions;

public class MaximumPlayersExceededException extends RuntimeException{
    public MaximumPlayersExceededException(String message) {
        super(message);
    }
}
