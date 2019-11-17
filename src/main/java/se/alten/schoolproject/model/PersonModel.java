package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonModel implements Serializable {

    private String firstName;
    private String lastName;
    private String email;

    public PersonModel(Teacher teacher) {

        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
     }

    public PersonModel(Student student) {

        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
    }
}
