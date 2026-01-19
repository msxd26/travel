package pe.jsaire.springtravel.utils.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "User not found in %s";


    public UserNotFoundException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}
