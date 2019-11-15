package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.BaseModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

//@MappedSuperclass
public abstract class EntityUtil {


    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private Logger logger = Logger.getLogger("EntityUtil");


    protected <T extends EntityUtil> T create(String object, Class<T> targetClass) throws Exception{
        logger.info("--------------------------1");
        try{
            T entity = mapper.readValue(object, targetClass);
            logger.info("--------------------------2");

            validate(entity);
            logger.info("--------------------------3");

            return entity;

        }catch(Exception e){
            logger.info("--------------------------4");

            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid request-body: " + e.getMessage() );
        }
    }


    <M extends BaseModel, E extends EntityUtil> Set<E> parseModelsToEntities(
            Set<M> models, Class<M> constructorType, Class<E> targetClass ) throws Exception{

        Set<E> entities = new HashSet<>();
        models.forEach(LambdaExceptionWrapper.handlingConsumerWrapper(model ->
                entities.add(targetClass.getDeclaredConstructor(constructorType).newInstance(model)), Exception.class));

        return entities;
    }


    void validate(EntityUtil entity) throws Exception {

        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            List<ConstraintViolation<EntityUtil>> violations = new ArrayList<>(validator.validate(entity));

            if (!violations.isEmpty()) {

                throw new ResourceCreationException("Invalid value for: " + violations.get(0).getPropertyPath() + ", " + violations.get(0).getMessage());
            }
        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }
    }
}
