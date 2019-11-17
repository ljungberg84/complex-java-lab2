package se.alten.schoolproject.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@NoArgsConstructor
@Path("/subjects")
public class SubjectController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    private static final Logger logger = Logger.getLogger("SubjectController");


    @PUT
    @Produces(APPLICATION_JSON)
    @Path("/{title}/student/{email}")
    public Response addStudentToSubject(@PathParam("title") String subjectTitle, @PathParam("email") String studentEmail) throws Exception {

        logger.info("addStudent1");
        Subject subject = schoolAccessLocal.getSubjectByTitle(subjectTitle);
        logger.info("addStudent2");

        Student student = schoolAccessLocal.getStudent(studentEmail);
        logger.info("addStudent3");

        //student.getSubjects().add(subject);

        subject.getStudents().add(student);
        logger.info("addStudent4");

        //student.getSubjects().add(subject);
        logger.info("addStudent5");

        subject = schoolAccessLocal.updateSubject(subject);
        logger.info("addStudent6");

        //schoolAccessLocal.updateStudent(student);
        logger.info("addStudent7");

        return Response.status(Response.Status.OK).entity(subject).build();
    }


    @PUT
    @Produces(APPLICATION_JSON)
    @Path("/{title}/teacher/{email}")
    public Response addTeacherToSubject(@PathParam("title") String title, @PathParam("email") String email ) throws Exception {

        logger.info("addteaher1");

        Subject subject = schoolAccessLocal.getSubjectByTitle(title);
        logger.info("addteaher2");

        Teacher teacher = schoolAccessLocal.getTeacherByEmail(email);
        logger.info("addteaher3");
        //teacher.getSubjects().add(subject);
        logger.info("addteaher4");


        subject.setTeacher(teacher);

        logger.info("addteaher5");

        //schoolAccessLocal.updateTeacher(teacher);
        logger.info("addteaher6");

        subject = schoolAccessLocal.updateSubject(subject);
        logger.info("addteaher7");


        return Response.status(Response.Status.OK).entity(subject).build();
    }


    @GET
    @Produces(APPLICATION_JSON)
    public Response listSubjects(){

        List<Subject> subjects = schoolAccessLocal.listAllSubjects();

        return Response.status(Response.Status.OK).entity(subjects).build();
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response addSubjects(String subjectBody, @Context UriInfo uriInfo) throws Exception{

        Subject subject = new Subject(subjectBody);
        logger.info("1-----------------------------------------------");
        logger.info("subject controller: " + subject);
        logger.info("1-----------------------------------------------");
        Subject addedSubject = schoolAccessLocal.addSubject(subject);

        logger.info("1-----------------------------------------------");
        logger.info("subject controller 2: " + subject);
        logger.info("1-----------------------------------------------");

        return Response.status(Response.Status.CREATED).entity(addedSubject).build();
    }
}
