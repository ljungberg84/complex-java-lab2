package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parent;
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
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@NoArgsConstructor
@Path("/teachers")
public class TeacherController {

    @Inject
    private SchoolAccessLocal schoolAccessLocal;


    @GET
    @Produces(APPLICATION_JSON)
    public Response listTeachers() throws Exception {

        List<Teacher> teachers = schoolAccessLocal.listAllTeachers();

        return Response.status(Response.Status.OK).entity(teachers).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response addTeacher(@Context UriInfo uriInfo, String teacher) throws Exception {

        Teacher newTeacher = new Teacher(teacher);
        newTeacher = schoolAccessLocal.addTeacher(newTeacher);
        URI createdUri = uriInfo.getAbsolutePathBuilder().path(newTeacher.getEmail()).build();

        return Response.status(Response.Status.CREATED).entity(createdUri).build();
    }


//    @POST
//    @Produces(APPLICATION_JSON)
//    @Path("/{title}")
//    public Response addTeacherToSubject(@PathParam("title") String title, String email ) throws Exception {
//
//        Subject subject = schoolAccessLocal.getSubjectByTitle(title);
//        Teacher teacher = schoolAccessLocal.getTeacherByEmail(email);
//
//        subject.setTeacher(teacher);
//        subject = schoolAccessLocal.addSubject(subject);
//
//        return Response.status(Response.Status.OK).entity(subject).build();
//    }
}
