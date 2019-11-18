package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@NoArgsConstructor
@Path("/students")
public class StudentController {


    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Produces(APPLICATION_JSON)
    public Response listStudents() throws Exception {

        List<StudentModel> students = schoolAccessLocal.listAllStudents();

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
    public Response addStudent(String studentBody, @Context UriInfo uriInfo) throws Exception {

        Student student = new Student(studentBody);
        StudentModel addedStudent = schoolAccessLocal.addStudent(student);

        URI createdURI = uriInfo.getAbsolutePathBuilder().path(addedStudent.getEmail()).build();

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

        Student student = new Student(requestBody);
        StudentModel updatedStudent = schoolAccessLocal.updateStudent(student);

        URI createdURI = uriInfo.getAbsolutePathBuilder().path(updatedStudent.getEmail()).build();

        return Response.status(Response.Status.OK).entity(createdURI).build();
    }
}
