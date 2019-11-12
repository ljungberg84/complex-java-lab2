package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
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
@Path("/students")
public class StudentController {

    private static final Logger logger = Logger.getLogger("StudentController");


    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Produces(APPLICATION_JSON)
    public Response listStudents() throws Exception{

        List<Student> students = schoolAccessLocal.listAllStudents();
        return Response.ok(students).build();
    }


    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{email}")
    public Response getStudent(@PathParam("email") String email) throws Exception{

        StudentModel student = schoolAccessLocal.getStudent(email);

        return Response.status(Response.Status.OK).entity(student).build();
    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addStudent(String requestBody, @Context UriInfo uriInfo) throws Exception {

        StudentModel studentModel = StudentModel.create(requestBody);
        StudentModel addedStudentModel = schoolAccessLocal.addStudent(studentModel);

        URI createdURI = uriInfo.getAbsolutePathBuilder().path(addedStudentModel.getEmail()).build();

        return Response.status(Response.Status.CREATED).entity(createdURI).build();
    }


    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("/{email}")
    public Response deleteUser( @PathParam("email") String email) throws Exception{

        schoolAccessLocal.removeStudent(email);

        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateStudent( String requestBody, @Context UriInfo uriInfo) throws Exception {

        StudentModel studentModel = StudentModel.create(requestBody);
        StudentModel updatedStudentModel = schoolAccessLocal.updateStudent(studentModel);

        URI createdURI = uriInfo.getAbsolutePathBuilder().path(updatedStudentModel.getEmail()).build();

        return Response.status(Response.Status.OK).entity(createdURI).build();
    }
}
