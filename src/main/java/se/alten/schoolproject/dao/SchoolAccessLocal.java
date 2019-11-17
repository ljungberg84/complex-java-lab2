package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws Exception;

    Student addStudent(Student student) throws Exception;

    Student getStudent(String email) throws Exception;

    void removeStudent(String email) throws Exception;

    Student updateStudent(Student student) throws Exception;


    //void updateStudentPartial(String studentModel);

    List listAllSubjects();

    Subject addSubject(Subject subject) throws Exception;

    Subject getSubjectByTitle(String title) throws Exception;

    Subject updateSubject(Subject subject) throws Exception;

    List<Teacher>listAllTeachers() throws Exception;

    Teacher updateTeacher(Teacher teacher) throws Exception;

    Teacher addTeacher(Teacher teacher) throws Exception;

    Teacher getTeacherByEmail(String email) throws Exception;

    }
