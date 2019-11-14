package se.alten.schoolproject.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ThrowingConsumer;
import se.alten.schoolproject.model.MyModel;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

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
                .addClass(Subject.class)
                .addClass(SubjectModel.class)
                .addClass(ThrowingConsumer.class)
                .addClass(LambdaExceptionWrapper.class)
                .addClass(MyEntity.class)
                .addClass(MyModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Before
    public void setUp(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    //----------------Parse StudentModel to and from Student entity------------//
    //-------------------------------------------------------------------------//


    @Test
    public void parseStudentModelToStudentEntityTest(){

        Set<SubjectModel> subjectModels = new HashSet<>();
        SubjectModel sub1 = new SubjectModel();
        SubjectModel sub2 = new SubjectModel();

        sub1.setTitle("math");
        sub2.setTitle("geography");

        subjectModels.add(sub1);
        subjectModels.add(sub2);

        StudentModel studentModel = new StudentModel();

        studentModel.setFirstName("test");
        studentModel.setLastName("test");
        studentModel.setEmail("test@email.com");
        studentModel.setSubjects(subjectModels);

        try{
            Student student = new Student(studentModel);

            assertEquals(2, student.getSubjects().size());
        }catch(Exception e){

            fail();
        }
    }


    @Test
    public void parseStudentModelFromStudentEntityTest(){

        Set<Subject> subjects = new HashSet<>();
        Subject sub1 = new Subject();
        Subject sub2 = new Subject();
        sub1.setTitle("math");
        sub2.setTitle("geography");

        subjects.add(sub1);
        subjects.add(sub2);

        Student student = new Student();
        student.setFirstName("test");
        student.setLastName("test");
        student.setEmail("test@email.com");
        student.setSubjects(subjects);

        StudentModel studentModel = null;

        try{
            studentModel = StudentModel.create(student);
            assertEquals(2, studentModel.getSubjects().size());

        }catch(Exception e){

            fail();
        }
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
