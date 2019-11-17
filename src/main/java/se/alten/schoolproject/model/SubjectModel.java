package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SubjectModel extends BaseModel implements Serializable {


    private String title;
    private List<PersonModel> students = new ArrayList<>();
    private PersonModel teacher;


    public SubjectModel(String newSubject) throws Exception {

        SubjectModel subjectModel = super.create(newSubject, SubjectModel.class);
        this.title = subjectModel.getTitle();
        if(subjectModel.getStudents() != null && !subjectModel.getStudents().isEmpty()){

            this.students = subjectModel.getStudents();
        }
    }


    public SubjectModel(Subject subject ) throws Exception {

        this.title = subject.getTitle();
        if(subject.getTeacher() != null){
            this.teacher = new PersonModel(subject.getTeacher());
        }
        for (Student student : subject.getStudents()) {
            this.students.add(new PersonModel(student));
        }
        //this.students = super.parseEntitiesToModels(subject.getStudents(), Student.class, StudentModel.class);
    }
}
