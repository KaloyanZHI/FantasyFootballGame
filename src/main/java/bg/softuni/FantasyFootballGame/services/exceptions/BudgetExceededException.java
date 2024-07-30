package bg.softuni.FantasyFootballGame.services.exceptions;

public class BudgetExceededException extends RuntimeException{
    public BudgetExceededException(String message) {
        super(message);
    }
}
