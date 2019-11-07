package se.alten.schoolproject.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class StudentTest {

    //@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Student.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    //@Test
    public void createValidStudentTest() {

        String name = "test";
        String lastName = "test2";
        String email = "test@gmail.com";

        System.out.println("-------------------------------------------------");
        System.out.println("test");
        System.out.println("--------------------------------------------------");

        Student student = new Student("123", name, lastName, email);

        System.out.println(student.getForename());
        System.out.println(student.getLastname());
        System.out.println(student.getEmail());

        assert(student.getForename().equals(name));
        assert(student.getForename().equals(lastName));
        assert (student.getEmail().equals(email));
    }

    //@Test(expected = Exception.class)
    public void createWithInvalidEmailTest(){

        Student student = new Student("123", "sven", "andersson", null);
    }

}
