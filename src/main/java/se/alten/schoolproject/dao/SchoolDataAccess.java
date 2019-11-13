package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.errorhandling.LambdaExceptionWrapper;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

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

    @Override
    public List<StudentModel> listAllStudents() throws Exception{

        List<Student> students = studentTransactionAccess.listAllStudents();
        List<StudentModel> studentModels = new ArrayList<>();
        students.forEach(LambdaExceptionWrapper.handlingConsumerWrapper(student ->
                studentModels.add(StudentModel.create(student)), Exception.class));

        return studentModels;
    }

    @Override
    public StudentModel getStudent(String email) throws Exception{

        Student student = studentTransactionAccess.getStudent(email);

        return StudentModel.create(student);

    }

    @Override
    public StudentModel addStudent(StudentModel newStudent) throws Exception{

        Student persistedEntity = studentTransactionAccess.addStudent(Student.create(newStudent));

        return StudentModel.create(persistedEntity);
    }

    @Override
    public void removeStudent(String studentEmail) throws Exception{

        studentTransactionAccess.removeStudent(studentEmail);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) throws Exception{

        StudentModel studentToUpdate = getStudent(student.getEmail());
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());

        Student updatedStudent = studentTransactionAccess.updateStudent(Student.create(student));

        return StudentModel.create(updatedStudent);
    }

    @Override
    public List listAllSubjects() {
        return null;
    }

    @Override
    public SubjectModel addSubject(String subjectModel) {
        return null;
    }
}
