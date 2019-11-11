package se.alten.schoolproject.error;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = false)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
