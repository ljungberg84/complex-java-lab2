package se.alten.schoolproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.logging.Logger;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.BaseModel;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class EntityUtil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    protected <T extends EntityUtil> T create(String object, Class<T> targetClass) throws Exception{
        try{
            T entity = mapper.readValue(object, targetClass);
            validate(entity);

            return entity;

        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceCreationException("Invalid request-body: " + e.getMessage() );
        }
    }

    @JsonIgnore
    @Transient
    private Logger logger = Logger.getLogger(EntityUtil.class);



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

            throw new ResourceCreationException(e.getMessage());
        }
    }
}
