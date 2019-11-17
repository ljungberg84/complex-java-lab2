package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.jboss.resteasy.logging.Logger;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends EntityUtil implements Serializable {


    private static final long serialVersionUID = 1L;


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


    @ManyToMany(mappedBy = "students" , fetch = FetchType.LAZY)//, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Subject> subjects = new HashSet<>();


    @JsonIgnore
    @Transient
    private Logger logger = Logger.getLogger(Student.class);


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
