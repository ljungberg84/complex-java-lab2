package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Teacher extends EntityUtil {

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

    @OneToMany(mappedBy = "subjects")
    private Set<Subject> subjects = new HashSet<>();


    @JsonIgnore
    @Transient
    private Logger logger = Logger.getLogger("Student");


    public Teacher(String jsonBody) throws Exception{

        try{
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


//    public Teacher(TeacherModel teacherModel) throws Exception {
//
//        this.firstName = teacherModel.getFirstName();
//        this.lastName = teacherModel.getLastName();
//        this.email = teacherModel.getEmail();
//        //this.subject = super.parseModelsToEntities(teacherModel.getSubjects(), SubjectModel.class, Subject.class);
//
//        validate(this);
//    }
}
