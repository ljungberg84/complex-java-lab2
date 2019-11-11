package se.alten.schoolproject.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.dao.SchoolAccessRemote;
import se.alten.schoolproject.dao.SchoolDataAccess;
import se.alten.schoolproject.errorhandling.*;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.rest.StudentController;
import se.alten.schoolproject.transaction.StudentTransaction;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

@RunAsClient
@RunWith(Arquillian.class)
public class StudentControllerTest {


    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Student.class)
                .addClass(StudentModel.class)
                .addClass(ResourceCreationException.class)
                .addClass(SchoolAccessRemote.class)
                .addClass(SchoolDataAccess.class)
                .addClass(SchoolAccessLocal.class)
                .addClass(ErrorMessage.class)
                .addClass(GeneralExceptionMapper.class)
                .addClass(LambdaExceptionWrapper.class)
                .addClass(ResourceCreationException.class)
                .addClass(ResourceNotFoundException.class)
                .addClass(ThrowingConsumer.class)
                .addClass(StudentController.class)
                .addClass(StudentTransaction.class)
                .addClass(StudentTransactionAccess.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");

    }


    @Test
    public void listStudentsTest(){

    }
}
