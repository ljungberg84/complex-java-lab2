package se.alten.schoolproject.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectModel implements Serializable {


    private String title;

    @JsonIgnoreProperties(value = { "subjects" })
    private List<StudentModel> students = new ArrayList<>();


    @JsonIgnoreProperties(value = { "subjects" })
    private TeacherModel teacher;

    @JsonIgnore
    private static Logger logger = Logger.getLogger(SubjectModel.class);

    @JsonIgnore
    private static ModelMapper modelMapper = new ModelMapper();



    public static SubjectModel create(Subject subject){

        logger.info("Creating SubjectModel from entity");

        return modelMapper.map(subject, SubjectModel.class);
    }
}
