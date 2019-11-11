package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Logger;

@Entity
@Table(name="student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student implements Serializable {

    private static final Logger logger = Logger.getLogger("Student");



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    public static Student toEntity(StudentModel studentModel) {

        Student student = new Student();

        student.setFirstName(studentModel.getFirstName());
        student.setLastName(studentModel.getLastName());
        student.setEmail(studentModel.getEmail());

        return student;

//        JsonReader reader = Json.createReader(new StringReader(studentModel));
//        JsonObject jsonObject = reader.readObject();
//
//        Student student = new Student();
//        if ( jsonObject.containsKey("firstName")) {
//            student.setFirstName(jsonObject.getString("firstName"));
//        } else {
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'firstName'");
//        }
//
//        if ( jsonObject.containsKey("lastName")) {
//
//            student.setLastName(jsonObject.getString("lastName"));
//        } else {
//            //student.setLastName("");
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'lastName'");
//        }
//
//        if ( jsonObject.containsKey("email")) {
//            student.setEmail(jsonObject.getString("email"));
//        } else {
//            //student.setEmail("");
//            throw new IllegalArgumentException("Invalid requestBody: Missing 'email'");
//        }
//
    }
}
