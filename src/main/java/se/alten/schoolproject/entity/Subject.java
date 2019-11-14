package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject extends MyEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "title")
    private String title;


    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();


    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public Subject(String jsonBody) throws Exception{

        try{
            Subject subject = super.create(jsonBody, Subject.class);
            this.setTitle(subject.getTitle());

        }catch(Exception e){

            throw new ResourceCreationException("Invalid requestBody");
        }
    }


    public Subject(SubjectModel subjectModel) throws Exception {

        this.setTitle(subjectModel.getTitle());
        this.setStudents(super.parseModelsToEntities(subjectModel.getStudents(), StudentModel.class, Student.class));

        validate(this);
    }
}
