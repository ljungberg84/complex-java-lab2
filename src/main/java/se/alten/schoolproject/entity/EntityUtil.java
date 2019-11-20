package se.alten.schoolproject.entity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

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

            throw new ResourceCreationException("Invalid request-body: " + e.getMessage() );
        }
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
