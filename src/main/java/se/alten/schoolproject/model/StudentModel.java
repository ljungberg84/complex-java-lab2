package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel extends BaseModel implements Serializable {


    private String firstName;
    private String lastName;
    private String email;
    private Set<SubjectModel> subjects = new HashSet<>();
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
        this.subjects = super.parseEntitiesToModels(student.getSubjects(), Subject.class, SubjectModel.class);

        validate(this);


    }

//    public static StudentModel create(Student student ) throws Exception {
//
//        StudentModel studentModel = new StudentModel();
//
//        studentModel.setFirstName(student.getFirstName());
//        studentModel.setLastName(student.getLastName());
//        studentModel.setEmail(student.getEmail());
//
////        student.getSubjects().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(subject ->
////                studentModel.getSubjects().add(SubjectModel.create(subject)), Exception.class));
//
//        validate(studentModel);
//
//        return studentModel;
//    }
}
