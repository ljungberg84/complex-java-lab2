package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/subjects")
public class SubjectController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSubjects(){

        List<Subject> subjects = schoolAccessLocal.listAllSubjects();

        return Response.status(Response.Status.OK).entity(subjects).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubjects(String subject, @Context UriInfo uriInfo) throws Exception{

        SubjectModel subjectModel = SubjectModel.create(subject);
        SubjectModel addedSubject = schoolAccessLocal.addSubject(subjectModel);

        return Response.status(Response.Status.CREATED).entity(addedSubject).build();
    }
}
