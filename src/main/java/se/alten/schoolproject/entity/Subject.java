package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static Subject create(String subject) throws Exception{

        try{
            Subject newSubject = mapper.readValue(subject, Subject.class);
            validate(newSubject);

            return newSubject;

        }catch(Exception e){

            throw new ResourceCreationException("Invalid request-body");
        }
    }


    public static Subject create(SubjectModel subjectModel) {

        Subject subject = new Subject();
        subject.setTitle(subjectModel.getTitle());

        subjectModel.getStudents().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(studentModel ->
                subject.getStudents().add(Student.create(studentModel)), Exception.class));

        return subject;
    }


    private static void validate(Subject subject) throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<Subject>> violations = new ArrayList<>(validator.validate(subject));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
