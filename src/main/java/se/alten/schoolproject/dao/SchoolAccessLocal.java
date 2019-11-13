package se.alten.schoolproject.dao;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    //originally studentModel return type
    StudentModel addStudent(StudentModel studentModel) throws Exception;

    StudentModel getStudent(String email) throws Exception;

    void removeStudent(String email) throws Exception;

    StudentModel updateStudent(StudentModel student) throws Exception;


    //void updateStudentPartial(String studentModel);

    List listAllSubjects();

    SubjectModel addSubject(SubjectModel subjectModel) throws Exception;
}
