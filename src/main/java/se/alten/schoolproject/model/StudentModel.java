package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
public class StudentModel extends BaseModel implements Serializable  {


    private String firstName;
    private String lastName;
    private String email;
    private List<StudentSubjectModel> subjects = new ArrayList<>();
    private static final Logger logger = Logger.getLogger("StudentModel");


    public StudentModel(String newStudent) throws Exception {

        StudentModel studentModel = super.create(newStudent, StudentModel.class);

        this.firstName = studentModel.getFirstName();
        this.lastName = studentModel.getLastName();
        this.email = studentModel.getEmail();

        if(studentModel.getSubjects() != null && !studentModel.getSubjects().isEmpty()){

            this.subjects = studentModel.getSubjects();
        }
    }


    public StudentModel(Student student) throws Exception{

        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        for(Subject subject : student.getSubjects()){

            this.subjects.add(new StudentSubjectModel(subject));
        }
    }
}
