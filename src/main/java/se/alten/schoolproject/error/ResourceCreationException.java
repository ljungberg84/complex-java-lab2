package se.alten.schoolproject.error;


import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class ResourceCreationException extends RuntimeException {

    public ResourceCreationException(String message) {
        super("Could not create resource: " + message);
    }
}
