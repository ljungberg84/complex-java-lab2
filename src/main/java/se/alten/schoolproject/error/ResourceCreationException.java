package se.alten.schoolproject.error;

import javax.ejb.ApplicationException;

//TODO: rename this exception
@ApplicationException(rollback = false)
public class ResourceCreationException extends Exception {

    public ResourceCreationException(String message) {
        super("Could not create resource: " + message);
    }

    public ResourceCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
