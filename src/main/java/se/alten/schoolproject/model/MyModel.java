package se.alten.schoolproject.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

public class MyModel {


    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static MyModel create(String model, Class targetClass ) throws Exception {

        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(MyModel.class, targetClass);
            MyModel myModel = mapper.readValue(model, type);
            validate(myModel);

            return myModel;

        } catch (Exception e) {

            throw new ResourceCreationException("Failed to create subjectModel: invalid request-body");
        }
    }


    static void validate(MyModel model) throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<MyModel>> violations = new ArrayList<>(validator.validate(model));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
