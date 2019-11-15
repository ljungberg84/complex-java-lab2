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
import se.alten.schoolproject.model.BaseModel;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.validation.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class StudentEntityTest {

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
                .addClass(EntityUtil.class)
                .addClass(BaseModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setUp(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    //----------------Parse Student entity ------------------------------------//
    //-------------------------------------------------------------------------//


    @Test
    public void createFromString(){

        String studentRequest = "{"+
                "\t\"firstName\":\"test1\",\n" +
                "\t\"lastName\":\"test2\",\n" +
                "\t\"email\":\"3@gmail.com\"}";

        try{
            Student student = new Student(studentRequest);

            assertEquals("3@gmail.com",student.getEmail());
            assertEquals("test1", student.getFirstName());
            assertEquals("test2", student.getLastName());

        }catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }


    @Test
    public void createFromStringWithSubjects(){

        String studentRequest = "{"+
                "\t\"firstName\":\"test1\",\n" +
                "\t\"lastName\":\"test2\",\n" +
                "\t\"email\":\"3@gmail.com\", \"subjects\":[\"math\", \"music\"]}";

        System.out.println(studentRequest);

        try{
            Student student = new Student(studentRequest);
            System.out.println(student.getSubjects());

            assertEquals("3@gmail.com",student.getEmail());
            assertEquals("test1", student.getFirstName());
            assertEquals("test2", student.getLastName());
            assertEquals("math", student.getSubjects().get(0));

        }catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
    }


    @Test
    public void createFromStudentModel(){

        String firstName = "test";
        String lastName = "test";
        String email = "test@mail.com";

        StudentModel studentModel = new StudentModel();
        studentModel.setFirstName(firstName);
        studentModel.setLastName(lastName);
        studentModel.setEmail(email);
        try{
            Student student = (Student) new Student(studentModel);

            assertEquals(firstName, student.getFirstName());
            assertEquals(lastName, student.getLastName());
            assertEquals(email, student.getEmail());
        }catch(Exception e){
            fail();
        }


    }


    //------------------bean validation-----------------------------------------//
    //--------------------------------------------------------------------------//

    @Test(expected = ResourceCreationException.class)
    public void createInvalidThrowsExceptionTest() throws Exception{

        String studentRequest = "{" +
                "\t\"firstName\":" + null + "," +
                "\t\"lastName\":\"test2\",\n" +
                "\t\"email\":\"3@gmail.com\"}";

        new Student(studentRequest);
    }


    @Test
    public void createWithInvalidEmailTest(){

        Student student = new Student();
        student.setFirstName("test");
        student.setLastName("test");
        student.setEmail("test.com");

        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<Student> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("email must be valid format", validatorMessage);
        assertEquals("email", propertyPath.toString());
    }


    @Test
    public void createWithNullEmailTest(){

        Student student = new Student();
        student.setFirstName("test");
        student.setLastName("test");
        student.setEmail(null);
        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<Student> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("email must not be null", validatorMessage);
        assertEquals("email", propertyPath.toString());
    }


    @Test
    public void createWithNullNameTest(){

        Student student = new Student();

        student.setFirstName(null);
        student.setLastName("test");
        student.setEmail("test@test.com");

        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<Student> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("firstName must not be null", validatorMessage);
        assertEquals("firstName", propertyPath.toString());
    }


    @Test
    public void createWithNullLastNameTest(){

        Student student = new Student();
        student.setFirstName("test");
        student.setLastName(null);
        student.setEmail("test@test.com");
        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
        ConstraintViolation<Student> violation = violations.get(0);

        String validatorMessage = violation.getMessageTemplate();
        Path propertyPath = violation.getPropertyPath();

        assertEquals("lastName must not be null", validatorMessage);
        assertEquals("lastName", propertyPath.toString());
    }
}
