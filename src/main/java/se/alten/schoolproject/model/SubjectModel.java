package se.alten.schoolproject.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubjectModel extends BaseModel implements Serializable {


    private String title;

    @JsonIgnoreProperties(value = { "subjects" })
    private List<StudentModel> students = new ArrayList<>();


    @JsonIgnoreProperties(value = { "subjects" })
    private TeacherModel teacher;

    @JsonIgnore
    private static Logger logger = Logger.getLogger(SubjectModel.class);

    @JsonIgnore
    private static ModelMapper modelMapper = new ModelMapper();



    public SubjectModel(String newSubject) throws Exception {

        SubjectModel subjectModel = super.create(newSubject, SubjectModel.class);
        this.title = subjectModel.getTitle();
        if(subjectModel.getStudents() != null && !subjectModel.getStudents().isEmpty()){

            this.students = subjectModel.getStudents();
        }
    }


    public SubjectModel(Subject subject ) throws Exception {

        logger.info("Constructing SubjectModel from Entity");

        this.title = subject.getTitle();
        if(subject.getTeacher() != null){
            this.teacher = new TeacherModel(subject.getTeacher());
        }
        for (Student student : subject.getStudents()) {
            this.students.add(new StudentModel(student));
        }
    }

    public static SubjectModel create(Subject subject){

        logger.info("Creating SubjectModel from entity");

        return modelMapper.map(subject, SubjectModel.class);
    }
}
