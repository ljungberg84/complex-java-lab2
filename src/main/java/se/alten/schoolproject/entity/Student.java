package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.model.StudentModel;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject",
            joinColumns=@JoinColumn(name="student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private Set<Subject> subjects = new HashSet<>();

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
