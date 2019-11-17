package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

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


//--------------------------------------------------------------------------------------
// temp to check how id is handled for entity
//    @GET
//    @Produces(APPLICATION_JSON)
//    public Response listStudents() throws Exception{
//
//        List<Student> students = schoolAccessLocal.listAllStudents();
//        return Response.ok(students).build();
//    }
//--------------------------------------------------------------------------------------


    // temp to check how id is handled for entity
    @GET
    @Produces(APPLICATION_JSON)
    public Response listStudents() throws Exception {
        logger.info("get 1");

        List<Student> students = schoolAccessLocal.listAllStudents();
        logger.info("get 2");

        return Response.ok(students).build();
    }


    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{email}")
    public Response getStudent(@PathParam("email") String email) throws Exception{

        Student student = schoolAccessLocal.getStudent(email);

        //StudentModel studentModel = new StudentModel(student);

        return Response.status(Response.Status.OK).entity(student).build();
        //return Response.status(Response.Status.OK).entity(studentModel).build();

    }


    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addStudent(String studentBody, @Context UriInfo uriInfo) throws Exception {

        Student student = new Student(studentBody);
//        if(student.getSubjects() != null && !student.getSubjects().isEmpty()){
//            for (String subject: student.getSubjects()) {
//                student.getSubjectObjs().add(schoolAccessLocal.getSubjectByTitle(subject));
//            }
//        }
        Student addedStudent = schoolAccessLocal.addStudent(student);
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
        Student updatedStudent = schoolAccessLocal.updateStudent(student);

        URI createdURI = uriInfo.getAbsolutePathBuilder().path(updatedStudent.getEmail()).build();

        return Response.status(Response.Status.OK).entity(createdURI).build();
    }


    @PATCH
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/{email}/subject")
    public Response addSubjectToStudent(@PathParam("email") String email, String requestSubject) throws Exception{

        Student student = schoolAccessLocal.getStudent(email);
        Subject subject = new Subject(requestSubject);
        schoolAccessLocal.addSubject(subject);
        logger.info("1-----------------------------------------------");
        logger.info("subject: " + subject);
        logger.info("1-----------------------------------------------");
        logger.info("2");
        //student.getSubjectObjs().add(subject);
        logger.info("3");

        Student addedStudent = schoolAccessLocal.updateStudent(student);
        logger.info("4");

        return Response.status(Response.Status.OK).entity(addedStudent).build();
    }

    //TODO: maybe bring back  patch for easier adding of teachers and subjects. parse incomming body to student entity and have -
    //TODO: update partial method that sets all fields that is not null on incomming obj to retrieved object
    //TODO: or just make endpoint to add and remove subject for student
}
