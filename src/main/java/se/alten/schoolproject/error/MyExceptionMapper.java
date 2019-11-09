package se.alten.schoolproject.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    //public static final Logger logger = Logger.getLogger(.class);

    @Override
    public Response toResponse(IllegalArgumentException e) {

       // throw new IllegalStateException("foo3");

        System.out.println("------------------------------------------------");
        System.out.println("in illegal arg exception!!");
        System.out.println(e.getMessage());
        System.out.println("------------------------------------------------");

        return null;
    }
}
