package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
public class TeacherModel extends BaseModel implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private List<TeacherSubject> subjects = new ArrayList<>();
    private static final Logger logger = Logger.getLogger("StudentModel");


    public TeacherModel(String newTeacher) throws Exception {

        TeacherModel teacherModel = super.create(newTeacher, TeacherModel.class);
        this.firstName = teacherModel.getFirstName();
        this.lastName = teacherModel.getLastName();
        this.email = teacherModel.getEmail();
        if(teacherModel.getSubjects() != null){
           this.subjects.addAll(teacherModel.getSubjects());
        }
    }

    public TeacherModel(Teacher teacher)throws Exception{

        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
        for (Subject subject : teacher.getSubjects()){
            this.subjects.add(new TeacherSubject(subject));
        }
    }

    //TODO: add second constructor(Teacher teacher);
}
