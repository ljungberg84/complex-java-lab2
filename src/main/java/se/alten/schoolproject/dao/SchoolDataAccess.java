package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
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
    public StudentModel addStudent(String newStudent) throws ResourceCreationException {

        StudentModel studentModel = StudentModel.create(newStudent);
        Student persistedEntity = studentTransactionAccess.addStudent(Student.create(studentModel));

        return StudentModel.create(persistedEntity);


//        //boolean checkForEmptyVariables = Stream.of(studentToAdd.getFirstName(), studentToAdd.getLastName(), studentToAdd.getEmail()).anyMatch(String::isEmpty);
//
//        if (checkForEmptyVariables) {
//
//            throw new IllegalArgumentException("empty variables in add Student, SchoolDataAcess");
//            //studentToAdd.setFirstName("empty");
//            //return studentModel.create(studentToAdd);
//        } else {
//
//            studentTransactionAccess.addStudent(studentToAdd);
//
//            return studentModel.create(studentToAdd);
//        }
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
