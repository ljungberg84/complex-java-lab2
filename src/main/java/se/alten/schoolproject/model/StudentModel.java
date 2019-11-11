package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.error.ResourceCreationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private static final Logger logger = Logger.getLogger("StudentModel");

    private int id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;


    public static StudentModel create(String student ) throws ResourceCreationException {

        try (JsonReader reader = Json.createReader(new StringReader(student))){

            JsonObject jsonObject = reader.readObject();
            StudentModel studentModel = new StudentModel();

            studentModel.setFirstName(jsonObject.getString("firstName"));
            studentModel.setLastName(jsonObject.getString("lastName"));
            studentModel.setEmail(jsonObject.getString("email"));

            studentModel.validate();

            return studentModel;

        }catch(NullPointerException e){

            throw new ResourceCreationException("Failed to create studentModel: invalid request-body");
        }
    }


    public static StudentModel create(Student student ) throws ResourceCreationException {

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setEmail(student.getEmail());

        studentModel.validate();

        return studentModel;
    }


    private void validate() throws ResourceCreationException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(this));

        if(!violations.isEmpty()){

            //TODO: throw custom exception here
            throw new ResourceCreationException("Failed to create studentModel, invalid field: " +
                    violations.get(0).getPropertyPath() + ", message: " + violations.get(0).getMessageTemplate());
        }
    }
}
