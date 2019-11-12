package se.alten.schoolproject.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;

import javax.validation.*;

import java.util.*;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class studentModelTest {

    private Validator validator;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Student.class)
                .addClass(StudentModel.class)
                .addClass(ResourceCreationException.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //----------------StudentModel bean validation tests-----------------------//
    //-------------------------------------------------------------------------//

    @Test
    public void createValidStudentTest() {

        String firstName = "test";
        String lastName = "test2";
        String email = "test@gmail.com";

        StudentModel student = new StudentModel();

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);

        assert(student.getFirstName().equals(firstName));
        assert(student.getLastName().equals(lastName));
        assert (student.getEmail().equals(email));
    }


    @Test
    public void createWithInvalidEmailTest(){

        StudentModel student = new StudentModel();
        student.setFirstName("test");
        student.setLastName("test");
        student.setEmail("test.com");
        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<StudentModel> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("email must be valid format", validatorMessage);
        assertEquals("email", propertyPath.toString());
    }

    @Test
    public void createWithNullEmailTest(){

        StudentModel student = new StudentModel();
        student.setFirstName("test");
        student.setLastName("test");
        student.setEmail(null);
        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(student));

        ConstraintViolation<StudentModel> violation = violations.get(0);
        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("email must not be null", validatorMessage);
        assertEquals("email", propertyPath.toString());
    }

    @Test
    public void createWithNullNameTest(){

        StudentModel student = new StudentModel();
        student.setFirstName(null);
        student.setLastName("test");
        student.setEmail("test@test.com");
        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<StudentModel> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("firstName must not be null", validatorMessage);
        assertEquals("firstName", propertyPath.toString());
    }


    @Test
    public void createWithNullLastNameTest(){

        StudentModel student = new StudentModel();
        student.setFirstName("test");
        student.setLastName(null);
        student.setEmail("test@test.com");
        List<ConstraintViolation<StudentModel>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<StudentModel> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("lastName must not be null", validatorMessage);
        assertEquals("lastName", propertyPath.toString());
    }
}
