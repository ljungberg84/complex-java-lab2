package se.alten.schoolproject.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.alten.schoolproject.entity.EntityUtil;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseModel {

    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    protected <T extends BaseModel> T create(String modelBody, Class<T> targetClass ) throws Exception {

        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(BaseModel.class, targetClass);
            T model = mapper.readValue(modelBody, type);
            validate(model);

            return model;

        } catch (Exception e) {

            throw new ResourceCreationException("Failed to create subjectModel: invalid request-body");
        }
    }


    <M extends EntityUtil, E extends BaseModel> Set<E> parseEntitiesToModels(
            Set<M> entities, Class<M> constructorType, Class<E> targetClass ) throws Exception{

        Set<E> models = new HashSet<>();
        entities.forEach(LambdaExceptionWrapper.handlingConsumerWrapper(model ->
                models.add(targetClass.getDeclaredConstructor(constructorType).newInstance(model)), Exception.class));

        return models;
    }


    static void validate(BaseModel model) throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<BaseModel>> violations = new ArrayList<>(validator.validate(model));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
