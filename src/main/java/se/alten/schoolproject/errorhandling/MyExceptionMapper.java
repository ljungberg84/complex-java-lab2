package se.alten.schoolproject.errorhandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    //public static final Logger logger = Logger.getLogger(.class);

    @Override
    public Response toResponse(IllegalArgumentException e) {


        System.out.println("------------------------------------------------");
        System.out.println("in illegal arg exception!!");
        System.out.println(e.getMessage());
        System.out.println("------------------------------------------------");

        return null;
    }
}
