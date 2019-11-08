package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.io.StringReader;

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
    private String id;

    @NotNull(message = "Forename cannot be null")
    @Column(name = "forename")
    private String forename;

    @NotNull(message = "Lastname cannot be null")
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;

    public Student(@NotNull(message = "Forename cannot be null") String forename, @NotNull(message = "Lastname cannot be null") String lastname,  @Email(message = "Email should be valid") String email) {
        this.forename = forename;
        this.lastname = lastname;
        if(email == null)
            throw new NullPointerException();
        this.email = email;
    }

    public Student toEntity(String studentModel) {
        JsonReader reader = Json.createReader(new StringReader(studentModel));

        JsonObject jsonObject = reader.readObject();

        Student student = new Student();
        if ( jsonObject.containsKey("forename")) {
            student.setForename(jsonObject.getString("forename"));
        } else {
            student.setForename("");
        }

        if ( jsonObject.containsKey("lastname")) {
            student.setLastname(jsonObject.getString("lastname"));
        } else {
            student.setLastname("");
        }

        if ( jsonObject.containsKey("email")) {
            student.setEmail(jsonObject.getString("email"));
        } else {
            student.setEmail("");
        }

        return student;
    }
}
