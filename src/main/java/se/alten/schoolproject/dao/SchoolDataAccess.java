package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private static final Logger logger = Logger.getLogger("SchoolDataAccess");

    @Inject
    StudentTransactionAccess studentTransactionAccess;
    @Inject
    SubjectTransactionAccess subjectTransactionAccess;


//--------------------------------------------------------------------------------------
// temp to check how id is handled for entity
//    @Override
//    public List<StudentModel> listAllStudents() throws Exception{
//
//        List<Student> students = studentTransactionAccess.listAllStudents();
//        List<StudentModel> studentModels = new ArrayList<>();
//        students.forEach(LambdaExceptionWrapper.handlingConsumerWrapper(student ->
//                studentModels.add(StudentModel.create(student)), Exception.class));
//
//        return studentModels;
//    }
//--------------------------------------------------------------------------------------


    @Override
    public List<Student> listAllStudents() throws Exception{

        List<Student> students = studentTransactionAccess.listAllStudents();

        return students;
    }


    @Override
    public StudentModel getStudent(String email) throws Exception{

        Student student = studentTransactionAccess.getStudent(email);

        return StudentModel.create(student);

    }



//    @Override
//    public StudentModel addStudent(StudentModel studentModel) throws Exception{
//
//        Student persistedEntity = studentTransactionAccess.addStudent(Student.create(studentModel));
//
//        return StudentModel.create(persistedEntity);
//    }


    //temp for using just Student
    @Override
    public Student addStudent(Student student) throws Exception{

        //Student persistedEntity = studentTransactionAccess.addStudent(student);

        return studentTransactionAccess.addStudent(student);
    }

    @Override
    public void removeStudent(String studentEmail) throws Exception{

        studentTransactionAccess.removeStudent(studentEmail);
    }

    @Override
    public Student updateStudent(Student student) throws Exception{

        //Student updatedStudent = studentTransactionAccess.updateStudent(student);

        return studentTransactionAccess.updateStudent(student);
    }

    @Override
    public List<Subject> listAllSubjects() {

        List<Subject> subjects = subjectTransactionAccess.listAllSubjects();

        return subjects;
    }

    @Override
    public SubjectModel addSubject(Subject subject)  throws Exception{

        Subject addedSubject = subjectTransactionAccess.addSubject(subject);

        return SubjectModel.create(addedSubject);
    }
}
