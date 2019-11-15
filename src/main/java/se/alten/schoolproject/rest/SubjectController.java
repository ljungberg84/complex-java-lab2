package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@NoArgsConstructor
@Path("/subjects")
public class SubjectController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    private static final Logger logger = Logger.getLogger("SubjectController");


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSubjects(){

        List<Subject> subjects = schoolAccessLocal.listAllSubjects();

        return Response.status(Response.Status.OK).entity(subjects).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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
