package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List<Student> listAllStudents();
    Student getStudent(String email) throws Exception;
    Student addStudent(Student studentToAdd) throws Exception;
    void removeStudent(String email) throws Exception;
    Student updateStudent(Student student) throws Exception;
}
