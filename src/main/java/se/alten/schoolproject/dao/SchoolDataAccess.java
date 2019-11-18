package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {


    @Inject
    StudentTransactionAccess studentTransactionAccess;
    @Inject
    SubjectTransactionAccess subjectTransactionAccess;
    @Inject
    TeacherTransactionAccess teacherTransactionAccess;


    @Override
    public List<StudentModel> listAllStudents() throws Exception{

        List<Student> students = studentTransactionAccess.listAllStudents();

        List<StudentModel> studentModels = new ArrayList<>();

        for(Student student : students){
            studentModels.add(new StudentModel(student));
        }

        return studentModels;
    }


    @Override
    public StudentModel getStudent(String email) throws Exception{

        return new StudentModel(studentTransactionAccess.getStudent(email));
    }


    @Override
    public StudentModel addStudent(Student student) throws Exception{

        return new StudentModel(studentTransactionAccess.addStudent(student));
    }


    @Override
    public void removeStudent(String studentEmail) throws Exception{

        studentTransactionAccess.removeStudent(studentEmail);
    }


    @Override
    public StudentModel updateStudent(Student student) throws Exception{

        return new StudentModel(studentTransactionAccess.updateStudent(student));
    }


    @Override
    public List<SubjectModel> listAllSubjects() throws Exception{

        List<Subject> subjects = subjectTransactionAccess.listAllSubjects();
        List<SubjectModel> subjectModels = new ArrayList<>();

        for (Subject subject : subjects){

            subjectModels.add(new SubjectModel(subject));
        }

        return subjectModels;
    }


    @Override
    public SubjectModel addSubject(Subject subject)  throws Exception{

        return new SubjectModel(subjectTransactionAccess.addSubject(subject));
    }


    @Override
    public SubjectModel getSubjectByTitle(String title) throws Exception{

        return new SubjectModel(subjectTransactionAccess.getSubjectByTitle(title));
    }


    @Override
    public SubjectModel updateSubject(Subject subject) throws Exception{

        return new SubjectModel(subjectTransactionAccess.updateSubject(subject));
    }


    @Override
    public List<TeacherModel> listAllTeachers() throws Exception {

        List<Teacher> teachers = teacherTransactionAccess.listAllTeachers();
        List<TeacherModel> teacherModels = new ArrayList<>();

        for( Teacher teacher : teachers){

            teacherModels.add(new TeacherModel(teacher));
        }

        return teacherModels;
    }


    @Override
    public TeacherModel updateTeacher(Teacher teacher) throws Exception {

        return new TeacherModel(teacherTransactionAccess.updateTeacher(teacher));
    }


    @Override
    public TeacherModel addTeacher(Teacher teacher) throws Exception{

        return new TeacherModel(teacherTransactionAccess.addTeacher(teacher));
    }


    @Override
    public TeacherModel getTeacherByEmail(String email) throws Exception{

        return new TeacherModel(teacherTransactionAccess.getTeacherByEmail(email));
    }


    @Override
    public SubjectModel addStudentToSubject(String subjectTitle, String studentEmail) throws Exception{

        Subject subject = subjectTransactionAccess.getSubjectByTitle(subjectTitle);
        Student student = studentTransactionAccess.getStudent(studentEmail);

        subject.getStudents().add(student);

        return new SubjectModel(subjectTransactionAccess.updateSubject(subject));
    }


    @Override
    public SubjectModel addTeacherToSubject(String subjectTitle, String teacherEmail) throws Exception{

        Subject subject = subjectTransactionAccess.getSubjectByTitle(subjectTitle);
        Teacher teacher = teacherTransactionAccess.getTeacherByEmail(teacherEmail);

        subject.setTeacher(teacher);

        return new SubjectModel(subjectTransactionAccess.updateSubject(subject));
    }
}
