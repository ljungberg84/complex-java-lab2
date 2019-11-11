package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.error.MyException;
import se.alten.schoolproject.error.ResourceCreationException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private static final Logger logger = Logger.getLogger("SchoolDataAccess");

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Override
    public List listAllStudents(){

        return studentTransactionAccess.listAllStudents();
    }

    @Override
    public StudentModel addStudent(StudentModel newStudent) throws MyException{

        Student persistedEntity = studentTransactionAccess.addStudent(Student.create(newStudent));

        return StudentModel.create(persistedEntity);
    }

    @Override
    public void removeStudent(String studentEmail) {
        studentTransactionAccess.removeStudent(studentEmail);
    }

    @Override
    public void updateStudent( String forename,  String lastname, String email) {
        studentTransactionAccess.updateStudent(forename, lastname, email);
    }

    @Override
    public void updateStudentPartial( String studentModel) {
//        Student studentToUpdate = student.create(studentModel);
//        studentTransactionAccess.updateStudentPartial(studentToUpdate);
    }
}
