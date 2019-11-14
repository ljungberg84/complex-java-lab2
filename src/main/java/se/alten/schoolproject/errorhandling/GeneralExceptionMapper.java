package se.alten.schoolproject.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper <Exception> {

    private static final Logger logger = Logger.getLogger("GeneralExceptionMapper");


    @Override
    public Response toResponse(Exception e) {

        if(e instanceof ResourceCreationException){
            logger.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
        }

        if(e instanceof ResourceNotFoundException) {
            logger.info(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
        }
        logger.info(e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
