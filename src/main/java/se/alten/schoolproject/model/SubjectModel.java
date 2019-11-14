package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel extends BaseModel implements Serializable {


    private String title;
    private Set<StudentModel> students = new HashSet<>();


    public SubjectModel(String newSubject) throws Exception {

        SubjectModel subjectModel = super.create(newSubject, SubjectModel.class);
        this.title = subjectModel.getTitle();
        if(subjectModel.getStudents() != null && !subjectModel.getStudents().isEmpty()){

            this.students = subjectModel.getStudents();
        }
    }


    public SubjectModel(Subject subject ) throws Exception {

        this.title = subject.getTitle();
        this.students = super.parseEntitiesToModels(subject.getStudents(), Student.class, StudentModel.class);
    }
}
