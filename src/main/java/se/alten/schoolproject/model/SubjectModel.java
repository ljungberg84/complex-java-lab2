package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel extends MyModel implements Serializable {

    private Long id;

    @NotEmpty(message = "title must not be null")
    private String title;

    private Set<StudentModel> students = new HashSet<>();

//    public static SubjectModel create(String subject ) throws Exception {
//
//        try (JsonReader reader = Json.createReader(new StringReader(subject))) {
//
//            JsonObject jsonObject = reader.readObject();
//            SubjectModel subjectModel = new SubjectModel();
//
//            subjectModel.setTitle(jsonObject.getString("title"));
//
//            subjectModel.validate();
//
//            return subjectModel;
//
//        } catch (NullPointerException e) {
//
//            throw new ResourceCreationException("Failed to create subjectModel: invalid request-body");
//        }
//    }


    public static SubjectModel create(Subject subject ) throws Exception {

        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subject.getTitle());

        subject.getStudents().forEach(LambdaExceptionWrapper.handlingConsumerWrapper(student ->
                subjectModel.getStudents().add(StudentModel.create(student)), Exception.class));

        validate(subjectModel);

        return subjectModel;
    }
}
