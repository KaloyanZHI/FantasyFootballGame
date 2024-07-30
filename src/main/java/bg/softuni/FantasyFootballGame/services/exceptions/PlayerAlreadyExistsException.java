package bg.softuni.FantasyFootballGame.services.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException{
    private final String firstName;
    private final String lastName;
    public PlayerAlreadyExistsException(String message, String firstName, String lastName) {
        super(message);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
