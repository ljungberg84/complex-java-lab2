package se.alten.schoolproject.error;

//TODO: rename this exception
public class ResourceCreationException extends Exception {

    public ResourceCreationException(String message) {
        super("Could not create resource: " + message);
    }

    public ResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
