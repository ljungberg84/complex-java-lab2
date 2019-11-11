package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;

import javax.persistence.*;
import java.io.Serializable;

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

    public static Student create(StudentModel studentModel) {

        Student student = new Student();

        student.setFirstName(studentModel.getFirstName());
        student.setLastName(studentModel.getLastName());
        student.setEmail(studentModel.getEmail());

        return student;
    }
}
