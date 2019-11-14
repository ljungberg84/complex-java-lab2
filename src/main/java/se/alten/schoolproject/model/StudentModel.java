package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel extends MyModel implements Serializable {

    //TODO: remove these annotations and the tests for them
    @NotEmpty(message = "firstName must not be null")
    private String firstName;


    @NotEmpty(message = "lastName must not be null")
    private String lastName;


    @NotEmpty(message = "email must not be null")
    @Email(message = "email must be valid format")
    private String email;


    private Set<SubjectModel> subjects = new HashSet<>();


    private static final Logger logger = Logger.getLogger("StudentModel");


    public static StudentModel create(Student student ) throws Exception {

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setEmail(student.getEmail());

        student.getSubjects().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(subject ->
                studentModel.getSubjects().add(SubjectModel.create(subject)), Exception.class));

        validate(studentModel);

        return studentModel;
    }
}
