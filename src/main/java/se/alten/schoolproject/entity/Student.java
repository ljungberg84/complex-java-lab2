package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student extends EntityUtil implements Serializable {


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

//    @ManyToMany(targetEntity = Subject.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    @JoinTable(name = "student_subject",
//            joinColumns=@JoinColumn(name="student_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjectObjs = new HashSet<>();


    //@JsonIgnore
    //TODO: rename this field and set name for jackson to know
    @Transient
    private List<String> subjects = new ArrayList<>();



    @JsonIgnore
    @Transient
    private Logger logger = Logger.getLogger("Student");


    public Student(String jsonBody) throws Exception{

        try{
            Student student = super.create(jsonBody, Student.class);
            this.firstName = student.getFirstName();
            this.lastName= student.getLastName();
            this.email = student.getEmail();
            if(student.getSubjects() != null && !student.getSubjects().isEmpty()){

                this.subjects = student.getSubjects();
            }
            if(student.getSubjectObjs() != null && !student.getSubjectObjs().isEmpty()){

                this.subjectObjs = student.getSubjectObjs();
            }

        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid requestBody");
        }
    }


    public Student(StudentModel studentModel) throws Exception {

        this.firstName = studentModel.getFirstName();
        this.lastName = studentModel.getLastName();
        this.email = studentModel.getEmail();
        this.subjectObjs = super.parseModelsToEntities(studentModel.getSubjects(), SubjectModel.class, Subject.class);

        validate(this);
    }
}
