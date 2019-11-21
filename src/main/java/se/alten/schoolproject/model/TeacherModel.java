package se.alten.schoolproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnoreProperties(value = { "teacher" })
    private List<SubjectModel> subjects = new ArrayList<>();

    @JsonIgnore
    private static Logger logger = Logger.getLogger(TeacherModel.class);

    @JsonIgnore
    private static ModelMapper modelMapper = new ModelMapper();



    public static TeacherModel create(Teacher teacher){

        logger.info("Creating TeacherModel from entity");

        return modelMapper.map(teacher, TeacherModel.class);
    }
}
