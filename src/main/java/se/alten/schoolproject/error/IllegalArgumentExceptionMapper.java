package se.alten.schoolproject.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    //public static final Logger logger = Logger.getLogger(.class);

    @Override
    public Response toResponse(IllegalArgumentException e) {

        return Response.status(Response.Status.CONFLICT).entity((e.getMessage())).build();
        //return Response.status(Response.Status.BAD_REQUEST).entity(String.format("Error: %s", e.getMessage())).build(); }

    }
    //@Override
//    public Response toResponse(IllegalArgumentException e) {
//
//       // throw new IllegalStateException("foo3");
//
//        System.out.println("------------------------------------------------");
//        System.out.println("in illegal arg exception!!");
//        System.out.println(e.getMessage());
//        System.out.println("------------------------------------------------");
//
//        return Response.status(Response.Status.OK).entity(String.format("Error: %s", e.getMessage())).build();
//    }
}
