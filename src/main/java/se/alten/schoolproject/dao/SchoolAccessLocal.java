package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Local;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List<StudentModel> listAllStudents() throws Exception;

    StudentModel addStudent(Student student) throws Exception;

    StudentModel getStudent(String email) throws Exception;

    void removeStudent(String email) throws Exception;

    StudentModel updateStudent(Student student) throws Exception;

    List<SubjectModel> listAllSubjects() throws Exception;

    SubjectModel addSubject(Subject subject) throws Exception;

    SubjectModel addStudentToSubject(String subjectTitle, String studentEmail) throws Exception;

    SubjectModel addTeacherToSubject(String subjectTitle, String teacherEmail) throws Exception;


    SubjectModel getSubjectByTitle(String title) throws Exception;

    SubjectModel updateSubject(Subject subject) throws Exception;

    List<TeacherModel>listAllTeachers() throws Exception;

    TeacherModel updateTeacher(Teacher teacher) throws Exception;

    TeacherModel addTeacher(Teacher teacher) throws Exception;

    TeacherModel getTeacherByEmail(String email) throws Exception;
    }
