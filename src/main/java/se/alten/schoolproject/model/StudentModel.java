package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;

import javax.inject.Inject;
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
import java.util.Set;
import java.util.logging.Logger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    //@Inject
    //private Validator validator;
    private static final Logger logger = Logger.getLogger("StudentModel");


    private int id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;

    public static StudentModel create(String student ) {

        logger.info("1");


        JsonReader reader = Json.createReader(new StringReader(student));

        JsonObject jsonObject = reader.readObject();
        StudentModel studentModel = new StudentModel();
        logger.info("2");



        studentModel.setFirstName(jsonObject.getString("firstName"));
        studentModel.setLastName(jsonObject.getString("lastName"));
        studentModel.setEmail(jsonObject.getString("email"));
        logger.info("3");

        logger.info(studentModel.toString());


        studentModel.validate();
        logger.info("4");


        return studentModel;

//        if ( jsonObject.containsKey("firstName")) {
//            studentModel.setFirstName(jsonObject.getString("firstName"));
//        } else {
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'firstName'");
//        }
//
//        if ( jsonObject.containsKey("lastName")) {
//
//            student.setLastName(jsonObject.getString("lastName"));
//        } else {
//            //student.setLastName("");
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'lastName'");
//        }
//
//        if ( jsonObject.containsKey("email")) {
//            student.setEmail(jsonObject.getString("email"));
//        } else {
//            //student.setEmail("");
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'email'");
//        }
    }

    public static StudentModel create(Student student ) {

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setEmail(student.getEmail());

        studentModel.validate();

        return studentModel;


//        switch (student.getFirstName()) {
//
//            case "empty":
//                studentModel.setFirstName("empty");
//                return studentModel;
//            case "duplicate":
//                studentModel.setFirstName("duplicate");
//                return studentModel;
//            default:
//                studentModel.setFirstName(student.getFirstName());
//                studentModel.setLastName(student.getLastName());
//                studentModel.setEmail(student.getEmail());
//                logger.info("2");
//
//                return studentModel;
//        }
    }

    private void validate(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        logger.info("validation1");

        //List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(model));
        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(this));

        logger.info("validation2");

        if(!violations.isEmpty()){
            logger.info("exception in validate");

            throw new IllegalArgumentException("failed to create studentModel: " + violations.get(0).getMessageTemplate());
        }
    }
}
