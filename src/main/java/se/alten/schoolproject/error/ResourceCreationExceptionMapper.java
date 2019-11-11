package se.alten.schoolproject.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ResourceCreationExceptionMapper implements ExceptionMapper<ResourceCreationException> {

    @Override
    public Response toResponse(ResourceCreationException e) {

        return Response.status(Response.Status.BAD_REQUEST).entity(("Error: " + e.getMessage())).build();
    }
}
