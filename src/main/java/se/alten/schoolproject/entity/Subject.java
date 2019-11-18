package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.log4j.Logger;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends EntityUtil implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "subject_student",
        joinColumns = {@JoinColumn(name = "subject_id")},
        inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> students = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "subject_teacher",
    joinColumns = {@JoinColumn(name = "subject_id")},
    inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
    private Teacher teacher;

    @JsonIgnore
    @Transient
    private Logger logger = Logger.getLogger(Subject.class);


    public Subject(String newSubject) throws Exception{

        try{
            logger.info("Creating Subject");

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


//    public Subject(SubjectModel subjectModel) throws Exception {
//
//        this.title = subjectModel.getTitle();
//        for (Student student : subjectModel.getStudents()){
//
//        }
//            //this.students = super.parseModelsToEntities(subjectModel.getStudents(), StudentModel.class, Student.class);
//
//        validate(this);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) &&
                title.equals(subject.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
