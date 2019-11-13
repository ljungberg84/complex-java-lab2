package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public static Subject create(SubjectModel subjectModel) {

        Subject subject = new Subject();
        subject.setTitle(subjectModel.getTitle());

        subjectModel.getStudents().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(studentModel ->
                subject.getStudents().add(Student.create(studentModel)), Exception.class));

        return subject;
    }
}
