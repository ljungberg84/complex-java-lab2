package se.alten.schoolproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.log4j.Logger;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends EntityUtil implements Serializable {


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

    @OneToMany(mappedBy = "teacher")//, fetch = FetchType.EAGER)//, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Subject> subjects = new HashSet<>();

    @JsonIgnore
    @Transient
    private static final Logger logger = Logger.getLogger(Teacher.class);


    public Teacher(String jsonBody) throws Exception{

        try{
            logger.info("Creating Teacher");

            Teacher teacher = super.create(jsonBody, Teacher.class);
            this.firstName = teacher.getFirstName();
            this.lastName= teacher.getLastName();
            this.email = teacher.getEmail();
            if(teacher.getSubjects() != null){

                this.subjects = teacher.getSubjects();
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
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) &&
                firstName.equals(teacher.firstName) &&
                lastName.equals(teacher.lastName) &&
                email.equals(teacher.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}
