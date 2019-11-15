package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherModel extends BaseModel {

    private String firstName;
    private String lastName;
    private String email;
    private Subject subject;
    private static final Logger logger = Logger.getLogger("StudentModel");


    public TeacherModel(String newTeacher) throws Exception {

        TeacherModel teacherModel = super.create(newTeacher, TeacherModel.class);
        this.firstName = teacherModel.getFirstName();
        this.lastName = teacherModel.getLastName();
        this.email = teacherModel.getEmail();
        if(teacherModel.getSubject() != null){
            this.subject = teacherModel.getSubject();
        }
    }
    //TODO: add second constructor(Teacher teacher);
}
