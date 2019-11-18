package se.alten.schoolproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TeacherSubjectModel implements Serializable {

    private String title;
    private List<PersonModel> students = new ArrayList<>();

    public TeacherSubjectModel(Subject subject) {

        this.title = subject.getTitle();
        for(Student student : subject.getStudents()){

            this.students.add(new PersonModel(student));
        }
    }
}
