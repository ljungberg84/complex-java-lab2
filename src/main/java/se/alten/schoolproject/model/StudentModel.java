package se.alten.schoolproject.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

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



    public static StudentModel create(Student student) throws Exception{
        try{
            logger.info("Creating StudentModel from entity");

            return modelMapper.map(student, StudentModel.class);
        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException("Error creating StudentModel from entity");
        }
    }
}
