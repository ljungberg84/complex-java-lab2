package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.TeacherModel;

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
@Path("/teachers")
public class TeacherController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;

    private static final Logger logger = Logger.getLogger("TeacherController");



    @GET
    @Produces(APPLICATION_JSON)
    public Response listTeachers() throws Exception {

        List<TeacherModel> teachers = schoolAccessLocal.listAllTeachers();

        return Response.status(Response.Status.OK).entity(teachers).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addTeacher(@Context UriInfo uriInfo, String teacher) throws Exception {
        logger.info("1");

        Teacher newTeacher = new Teacher(teacher);
        logger.info("2");
        TeacherModel addedTeacher = schoolAccessLocal.addTeacher(newTeacher);
        logger.info("3");
        logger.info(addedTeacher.toString());

        URI createdUri = uriInfo.getAbsolutePathBuilder().path(addedTeacher.getEmail()).build();

        logger.info("4");

        return Response.status(Response.Status.CREATED).entity(createdUri).build();
    }
}
