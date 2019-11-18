package se.alten.schoolproject.errorhandling;


import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper <Exception> {


    private Logger logger = Logger.getLogger(GeneralExceptionMapper.class);


    @Override
    public Response toResponse(Exception e) {

        if(e instanceof ResourceCreationException){

            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e.getMessage())).build();
        }

        if(e instanceof ResourceNotFoundException) {
            logger.info(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(e.getMessage())).build();
        }
        logger.info(e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorMessage(e.getMessage() + e.getStackTrace().toString())).build();
    }
}
