package se.alten.schoolproject.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.ssl.ApplicationProtocolNames;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.plugins.providers.atom.Person;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

    @Inject
    private ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger("SubjectController");


    @POST
    @Produces(APPLICATION_JSON)
    @Path("/{title}")
    public Response addStudentToSubject(@PathParam("title") String title, String requestBody ) throws Exception {

        Subject subject = schoolAccessLocal.getSubjectByTitle(title);

        List<String> emails = mapper.readValue(requestBody, List.class);
        for (String email: emails) {

            Student student = schoolAccessLocal.getStudent(email);
            subject.getStudents().add(student);
        }
        subject = schoolAccessLocal.addSubject(subject);

        return Response.status(Response.Status.OK).entity(subject).build();
    }


    @POST
    @Produces(APPLICATION_JSON)
    @Path("/{title}")
    public Response addTeacherToSubject(@PathParam("title") String title, String email ) throws Exception {

        System.out.println("----------------------");
        System.out.println("email in addStudentToSubject: " + email);
        System.out.println("----------------------");

        Subject subject = schoolAccessLocal.getSubjectByTitle(title);
        Teacher teacher = schoolAccessLocal.getTeacherByEmail(email);

        subject.setTeacher(teacher);
        subject = schoolAccessLocal.addSubject(subject);

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
