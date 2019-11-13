package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;

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
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "firstName must not be empty")
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty(message = "lastName must not be empty")
    @Column(name = "lastName")
    private String lastName;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be valid format")
    @Column(name = "email", unique = true)
    private String email;

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject",
            joinColumns=@JoinColumn(name="student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private Set<Subject> subjects = new HashSet<>();

    public static Student create(String student) throws Exception{

        try{
            System.out.println("0------------------------------------------------------------------");
            Student newStudent = mapper.readValue(student, Student.class);
            System.out.println("1------------------------------------------------------------------");

            System.out.println(newStudent);
            System.out.println("-------------------------------------------------------------------");

            return newStudent;

        }catch(Exception e){

            throw new ResourceCreationException("Could not create Student entity");
        }
    }

    public static Student create(StudentModel studentModel) {

        Student student = new Student();

        student.setFirstName(studentModel.getFirstName());
        student.setLastName(studentModel.getLastName());
        student.setEmail(studentModel.getEmail());

        studentModel.getSubjects().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(subjectModel ->
                student.getSubjects().add(Subject.create(subjectModel)), Exception.class));

        return student;
    }
}
