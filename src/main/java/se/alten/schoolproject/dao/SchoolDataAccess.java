package se.alten.schoolproject.dao;

import org.apache.log4j.Logger;
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

    private static Logger logger = Logger.getLogger(SchoolAccessLocal.class);


    @Override
    public List<StudentModel> listAllStudents() throws Exception{

        List<Student> students = studentTransactionAccess.listAllStudents();
        List<StudentModel> studentModels = new ArrayList<>();

        for(Student student : students){
            studentModels.add(StudentModel.create(student));
        }

        return studentModels;
    }


    @Override
    public StudentModel getStudent(String email) throws Exception{

        return StudentModel.create(studentTransactionAccess.getStudent(email));
    }


    @Override
    public StudentModel addStudent(Student student) throws Exception{

        return StudentModel.create(studentTransactionAccess.addStudent(student));
    }


    @Override
    public void removeStudent(String studentEmail) throws Exception{

        studentTransactionAccess.removeStudent(studentEmail);
    }


    @Override
    public StudentModel updateStudent(Student student) throws Exception{

        return StudentModel.create(studentTransactionAccess.updateStudent(student));
    }


    @Override
    public List<SubjectModel> listAllSubjects() throws Exception{

        List<Subject> subjects = subjectTransactionAccess.listAllSubjects();
        List<SubjectModel> subjectModels = new ArrayList<>();

        for (Subject subject : subjects){

            subjectModels.add(SubjectModel.create(subject));
        }

        return subjectModels;
    }


    @Override
    public SubjectModel addSubject(Subject subject)  throws Exception{

        return SubjectModel.create(subjectTransactionAccess.addSubject(subject));
    }


    @Override
    public SubjectModel getSubjectByTitle(String title) throws Exception{

        return SubjectModel.create(subjectTransactionAccess.getSubjectByTitle(title));
    }


    @Override
    public SubjectModel updateSubject(Subject subject) throws Exception{

        return SubjectModel.create(subjectTransactionAccess.updateSubject(subject));
    }


    @Override
    public List<TeacherModel> listAllTeachers() throws Exception {

        List<Teacher> teachers = teacherTransactionAccess.listAllTeachers();
        List<TeacherModel> teacherModels = new ArrayList<>();

        for( Teacher teacher : teachers){

            teacherModels.add(TeacherModel.create(teacher));
        }

        return teacherModels;
    }


    @Override
    public TeacherModel updateTeacher(Teacher teacher) throws Exception {

        return TeacherModel.create(teacherTransactionAccess.updateTeacher(teacher));
    }


    @Override
    public TeacherModel addTeacher(Teacher teacher) throws Exception{

        return TeacherModel.create(teacherTransactionAccess.addTeacher(teacher));
    }


    @Override
    public TeacherModel getTeacherByEmail(String email) throws Exception{

        return TeacherModel.create(teacherTransactionAccess.getTeacherByEmail(email));
    }


    @Override
    public SubjectModel addStudentToSubject(String subjectTitle, String studentEmail) throws Exception{

        logger.info("Retrieving Subject to append to");
        Subject subject = subjectTransactionAccess.getSubjectByTitle(subjectTitle);
        logger.info("Retrieving Student to append  subject");
        Student student = studentTransactionAccess.getStudent(studentEmail);

        logger.info("Adding  Student to subject");

        subject.getStudents().add(student);

        logger.info("Returning response");

        return SubjectModel.create(subjectTransactionAccess.updateSubject(subject));
    }


    @Override
    public SubjectModel addTeacherToSubject(String subjectTitle, String teacherEmail) throws Exception{

        Subject subject = subjectTransactionAccess.getSubjectByTitle(subjectTitle);
        Teacher teacher = teacherTransactionAccess.getTeacherByEmail(teacherEmail);

        subject.setTeacher(teacher);

        return SubjectModel.create(subjectTransactionAccess.updateSubject(subject));
    }
}
