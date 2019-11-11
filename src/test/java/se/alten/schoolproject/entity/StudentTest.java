//package se.alten.schoolproject.entity;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.validation.*;
//
//import java.util.*;
//
//import static org.junit.Assert.*;
//
//
//@RunWith(Arquillian.class)
//public class StudentTest {
//
//    private Validator validator;
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(Student.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @Before
//    public void setUp(){
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//
//    @Test
//    public void createValidStudentTest() {
//
//        String name = "test";
//        String lastName = "test2";
//        String email = "test@gmail.com";
//
//        Student student = new Student( name, lastName, email);
//
//        assert(student.getFirstName().equals(name));
//        assert(student.getLastName().equals(lastName));
//        assert (student.getEmail().equals(email));
//    }
//
//
//    //@Test(expected = NullPointerException.class)
////    public void createWithNullEmailTest(){
////
////        Student student = new Student( "sven", "andersson", null);
////    }
//
//    //----------------Student bean validation tests----------------------------
//    //-------------------------------------------------------------------------
//
//    @Test
//    public void createWithInvalidEmailTest(){
//
//        Student student = new Student( "sven", "andersson",  "invalidEmail");
//        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
//        ConstraintViolation<Student> violation = violations.get(0);
//
//        String validatorMessage = violation.getMessageTemplate();
//        Path propertyPath = violation.getPropertyPath();
//
//        assertEquals("Email should be valid", validatorMessage);
//        assertEquals("email", propertyPath.toString());
//    }
//
//    @Test
//    public void createWithNullEmailTest(){
//
//        Student student = new Student( "sven", "andersson",  null);
//        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
//
//        ConstraintViolation<Student> violation = violations.get(0);
//        String validatorMessage = violation.getMessageTemplate();
//        Path propertyPath = violation.getPropertyPath();
//
//        assertEquals("Email cannot be null", validatorMessage);
//        assertEquals("email", propertyPath.toString());
//    }
//
//    @Test
//    public void createWithNullNameTest(){
//
//        Student student = new Student( null, "andersson",  "svenne@gmail.com");
//        List<ConstraintViolation<Student>> violations = new ArrayList<>(validator.validate(student));
//        ConstraintViolation<Student> violation = violations.get(0);
//
//        String validatorMessage = violation.getMessageTemplate();
//        Path propertyPath = violation.getPropertyPath();
//
//        System.out.println("------------------------------------------------");
//        System.out.println(validatorMessage);
//        System.out.println(propertyPath);
//        System.out.println("------------------------------------------------");
//
//        assertEquals("Forename cannot be null", validatorMessage);
//        assertEquals("firstName", propertyPath.toString());
//    }
//}
