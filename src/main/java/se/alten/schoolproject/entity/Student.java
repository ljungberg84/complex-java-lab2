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
import java.util.*;
import java.util.logging.Logger;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends EntityUtil implements Serializable {


    private static final long serialVersionUID = 1L;

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


    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)//, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //@JsonBackReference
    private Set<Subject> subjects = new HashSet<>();



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


        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid requestBody");
        }
    }


//    public Student(StudentModel studentModel) throws Exception {
//
//        this.firstName = studentModel.getFirstName();
//        this.lastName = studentModel.getLastName();
//        this.email = studentModel.getEmail();
//        this.subjects = super.parseModelsToEntities(studentModel.getSubjects(), SubjectModel.class, Subject.class);
//
//        validate(this);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}
