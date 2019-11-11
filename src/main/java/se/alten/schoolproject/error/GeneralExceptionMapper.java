package se.alten.schoolproject.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper <Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.BAD_REQUEST).entity((e.getMessage())).build();
    }
}
