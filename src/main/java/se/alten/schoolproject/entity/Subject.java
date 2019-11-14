package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jboss.logging.Logger;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity//(name = "subject")
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject extends EntityUtil implements Serializable {

//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    @NotEmpty
    @Column(name = "title")
    private String title;


    @ManyToMany(targetEntity = Student.class, mappedBy = "subjects", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<Student> students = new HashSet<Student>();


    @JsonIgnore
    private org.jboss.logging.Logger logger = Logger.getLogger(getClass());


    public Subject(String newSubject) throws Exception{

        try{
            Subject subject = super.create(newSubject, Subject.class);
            this.title = subject.getTitle();
            if(subject.getStudents() != null && !subject.getStudents().isEmpty()){

                this.students = subject.getStudents();
            }
        }catch(Exception e){
            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid requestBody");
        }
    }


    public Subject(SubjectModel subjectModel) throws Exception {

        this.title = subjectModel.getTitle();
        this.students = super.parseModelsToEntities(subjectModel.getStudents(), StudentModel.class, Student.class);

        validate(this);
    }
}
