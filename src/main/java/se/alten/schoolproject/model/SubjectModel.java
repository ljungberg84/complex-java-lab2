package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotEmpty;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "title must not be null")
    @Column
    private String title;

    private Set<StudentModel> students;

    public static SubjectModel create(String subject ) throws Exception {

        try (JsonReader reader = Json.createReader(new StringReader(subject))) {

            JsonObject jsonObject = reader.readObject();
            SubjectModel subjectModel = new SubjectModel();

            subjectModel.setTitle(jsonObject.getString("title"));

            subjectModel.validate();

            return subjectModel;

        } catch (NullPointerException e) {

            throw new ResourceCreationException("Failed to create subjectModel: invalid request-body");
        }
    }


    public static SubjectModel create(Subject subject ) throws Exception {

        SubjectModel subjectModel = new SubjectModel();

        subjectModel.setTitle(subject.getTitle());

        subject.getStudents().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(student ->
                subjectModel.getStudents().add(StudentModel.create(student)), Exception.class));

        subjectModel.validate();

        return subjectModel;
    }


    private void validate() throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<SubjectModel>> violations = new ArrayList<>(validator.validate(this));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
