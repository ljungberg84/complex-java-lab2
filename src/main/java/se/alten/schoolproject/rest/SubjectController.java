package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.SubjectModel;

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

        //SubjectModel subject = schoolAccessLocal.getSubjectByTitle(subjectTitle);

        //Student student = schoolAccessLocal.getStudent(studentEmail);

        SubjectModel updatedSubject = schoolAccessLocal.addStudentToSubject(subjectTitle, studentEmail);


        //subject.getStudents().add(student);


        //subject = schoolAccessLocal.updateSubject(subject);


        return Response.status(Response.Status.OK).entity(updatedSubject).build();
    }


    @PUT
    @Produces(APPLICATION_JSON)
    @Path("/{title}/teacher/{email}")
    public Response addTeacherToSubject(@PathParam("title") String subjectTitle, @PathParam("email") String teacherEmail ) throws Exception {

        SubjectModel updatedSubject = schoolAccessLocal.addTeacherToSubject(subjectTitle, teacherEmail);

        return Response.status(Response.Status.OK).entity(updatedSubject).build();
    }


    @GET
    @Produces(APPLICATION_JSON)
    public Response listSubjects() throws Exception{

        List<SubjectModel> subjects = schoolAccessLocal.listAllSubjects();

        return Response.status(Response.Status.OK).entity(subjects).build();
    }

    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response addSubjects(String subjectBody, @Context UriInfo uriInfo) throws Exception{

        Subject subject = new Subject(subjectBody);
        SubjectModel addedSubject = schoolAccessLocal.addSubject(subject);

        return Response.status(Response.Status.CREATED).entity(addedSubject).build();
    }
}
