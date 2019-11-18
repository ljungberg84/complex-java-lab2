package se.alten.schoolproject.errorhandling;


//@ApplicationException(rollback = false)
public class ResourceCreationException extends Exception {

    public ResourceCreationException(String message) {

        super(message);
    }
}
