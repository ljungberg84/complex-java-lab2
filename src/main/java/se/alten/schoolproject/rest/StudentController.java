package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

    private static final Logger logger = Logger.getLogger("StudentController");

    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    @GET
    @Produces(APPLICATION_JSON)
    public Response showStudents() {

        try {
            List<Student> students = schoolAccessLocal.listAllStudents();
            return Response.ok(students).build();

        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET//not implemented
    @Produces(APPLICATION_JSON)
    @Path("/{email}")
    public Response getStudent(){
        return Response.noContent().build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addStudent(String requestBody, @Context UriInfo uriInfo) {

        logger.info("1");
        StudentModel student = schoolAccessLocal.addStudent(requestBody);
        logger.info("2");
        URI createdURI = uriInfo.getBaseUriBuilder().path(student.getEmail()).build();
        logger.info("3");


        return Response.status(Response.Status.CREATED).entity(createdURI).build();
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser( @PathParam("email") String email) {

        try {
            schoolAccessLocal.removeStudent(email);
            return Response.ok().build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public void updateStudent( @QueryParam("firstName") String forename, @QueryParam("lastName") String lastname, @QueryParam("email") String email) {

        schoolAccessLocal.updateStudent(forename, lastname, email);
    }

    @PATCH
    public void updatePartialAStudent(String studentModel) {

        schoolAccessLocal.updateStudentPartial(studentModel);
    }
}
