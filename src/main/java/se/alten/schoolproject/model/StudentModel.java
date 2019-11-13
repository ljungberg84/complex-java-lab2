package se.alten.schoolproject.model;

import lombok.*;
import org.eclipse.jgit.diff.HashedSequence;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel implements Serializable {

    private static final Logger logger = Logger.getLogger("StudentModel");

    @NotEmpty(message = "firstName must not be null")
    private String firstName;

    @NotEmpty(message = "lastName must not be null")
    private String lastName;

    @NotEmpty(message = "email must not be null")
    @Email(message = "email must be valid format")
    private String email;

    private Set<SubjectModel> subjects = new HashSet<>();

//    //TODO: use jackson to parse to Model on all types?
//    public static StudentModel create(String student ) throws Exception {
//
//        try (JsonReader reader = Json.createReader(new StringReader(student))){
//
//            JsonObject jsonObject = reader.readObject();
//            StudentModel studentModel = new StudentModel();
//
//            studentModel.setFirstName(jsonObject.getString("firstName"));
//            studentModel.setLastName(jsonObject.getString("lastName"));
//            studentModel.setEmail(jsonObject.getString("email"));
//
//            studentModel.validate();
//
//            return studentModel;
//
//        }catch(NullPointerException e){
//
//            throw new ResourceCreationException("Failed to create studentModel: invalid request-body");
//        }
//    }


    public static StudentModel create(Student student ) throws Exception {

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setEmail(student.getEmail());

        student.getSubjects().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(subject ->
                studentModel.getSubjects().add(SubjectModel.create(subject)), Exception.class));

        //studentModel.validate();

        return studentModel;
    }


    private void validate() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(this));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
