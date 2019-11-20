package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import se.alten.schoolproject.dao.SchoolAccessLocal;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.SubjectModel;

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
@Path("/subjects")
public class SubjectController {


    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    private static Logger logger = Logger.getLogger(SubjectController.class);


    @PUT
    @Produces(APPLICATION_JSON)
    @Path("/{title}/student/{email}")
    public Response addStudentToSubject(@PathParam("title") String subjectTitle, @PathParam("email") String studentEmail) throws Exception {

        SubjectModel updatedSubject = schoolAccessLocal.addStudentToSubject(subjectTitle, studentEmail);

        logger.info("Building response");

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


    @GET
    @Path("/{title}")
    @Produces(APPLICATION_JSON)
    public Response getSubjectByTitle(@PathParam(value = "title") String title, @Context UriInfo uriInfo) throws Exception{

        SubjectModel subject = schoolAccessLocal.getSubjectByTitle(title);

        return Response.status(Response.Status.OK).entity(subject).build();
    }


    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response addSubjects(String subjectBody, @Context UriInfo uriInfo) throws Exception{

        Subject subject = new Subject(subjectBody);
        SubjectModel addedSubject = schoolAccessLocal.addSubject(subject);

        URI createdUri = uriInfo.getAbsolutePathBuilder().path(addedSubject.getTitle()).build();

        return Response.status(Response.Status.CREATED).entity(createdUri).build();
    }
}
