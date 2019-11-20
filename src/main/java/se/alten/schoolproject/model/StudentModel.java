package se.alten.schoolproject.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentModel implements Serializable  {


    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnoreProperties(value = { "students", "teacher.subjects" })
    private List<SubjectModel> subjects = new ArrayList<>();

    @JsonIgnore
    private static Logger logger = Logger.getLogger(StudentModel.class);

    @JsonIgnore
    private static ModelMapper modelMapper = new ModelMapper();


//    public StudentModel(String newStudent) throws Exception {
//
//        StudentModel studentModel = super.create(newStudent, StudentModel.class);
//
//        this.firstName = studentModel.getFirstName();
//        this.lastName = studentModel.getLastName();
//        this.email = studentModel.getEmail();
//
//        if(studentModel.getSubjects() != null && !studentModel.getSubjects().isEmpty()){
//
//            this.subjects = studentModel.getSubjects();
//        }
//    }
//
//
//    public StudentModel(Student student) throws Exception{
//
//        logger.info("Constructing StudentModel from Entity");
//
//        this.firstName = student.getFirstName();
//        this.lastName = student.getLastName();
//        this.email = student.getEmail();
//        for(Subject subject : student.getSubjects()){
//
//            this.subjects.add(new SubjectModel(subject));
//        }
//    }

    public static StudentModel create(Student student){

        logger.info("Creating StudentModel from entity");

        return modelMapper.map(student, StudentModel.class);
    }
}
