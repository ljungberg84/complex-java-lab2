package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List listAllStudents();
    Student getStudent(String email);
    Student addStudent(Student studentToAdd) throws Exception;
    void removeStudent(String email);
    Student updateStudent(Student student);
    void updateStudentPartial(Student studentToUpdate);
}
