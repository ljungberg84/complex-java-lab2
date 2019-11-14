package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public abstract class MyEntity {


    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    static final Logger logger = Logger.getLogger("MyEntity");


    protected <T extends MyEntity> T create(String object, Class<T> targetClass) throws Exception{

        try{
            T entity = mapper.readValue(object, targetClass);
            validate(entity);

            return entity;

        }catch(Exception e){
            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid request-body: " + e.getMessage() );
        }
    }


    static void validate(MyEntity entity) throws Exception {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<ConstraintViolation<MyEntity>> violations = new ArrayList<>(validator.validate(entity));

        if(!violations.isEmpty()){

            throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
        }
    }
}
