package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student extends MyEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "firstName must not be null")
    @Column(name = "firstName")
    private String firstName;


    @NotEmpty(message = "lastName must not be null")
    @Column(name = "lastName")
    private String lastName;


    @NotEmpty(message = "email must not be null")
    @Email(message = "email must be valid format")
    @Column(name = "email", unique = true)
    private String email;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject",
            joinColumns=@JoinColumn(name="student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private Set<Subject> subjects = new HashSet<>();


    public Student(String jsonBody) throws Exception{

        try{
            Student student = super.create(jsonBody, Student.class);
            this.setFirstName(student.getFirstName());
            this.setLastName(student.getLastName());
            this.setEmail(student.getEmail());

        }catch(Exception e){

            throw new ResourceCreationException("Invalid requestBody");
        }
    }


    public Student(StudentModel studentModel) throws Exception {

        this.setFirstName(studentModel.getFirstName());
        this.setLastName(studentModel.getLastName());
        this.setEmail(studentModel.getEmail());
        this.setSubjects(parseSubjects(studentModel.getSubjects()));

        validate(this);
    }


    private Set<Subject> parseSubjects(Set<SubjectModel> subjectModels){

        Set<Subject> subjects = new HashSet<>();
        subjectModels.forEach(LambdaExceptionWrapper.handlingConsumerWrapper( subjectModel ->
                subjects.add(new Subject(subjectModel)), Exception.class));

        return subjects;
    }
}
