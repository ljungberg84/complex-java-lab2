package se.alten.schoolproject.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class StudentTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Student.class)
                //.addClass(AssertionFailedError.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void createValidStudentTest() {

        String name = "test";
        String lastName = "test2";
        String email = "test@gmail.com";

        Student student = new Student( name, lastName, email);

        assert(student.getForename().equals(name));
        assert(student.getLastname().equals(lastName));
        assert (student.getEmail().equals(email));
    }

    @Test(expected = NullPointerException.class)
    public void createWithInvalidEmailTest(){

        Student student = new Student( "sven", "andersson", null);
    }

}
