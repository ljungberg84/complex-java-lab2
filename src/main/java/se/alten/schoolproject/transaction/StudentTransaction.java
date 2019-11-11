package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.error.ResourceCreationException;
import se.alten.schoolproject.error.ResourceNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess{

    private static final Logger logger = Logger.getLogger("StudentTransaction");


    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllStudents() {

        Query query = entityManager.createQuery("SELECT s from Student s");

        return query.getResultList();
    }

    @Override
    public Student getStudent(String email) {
        try{
            Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email");
            query.setParameter("email", email);

            return (Student)query.getSingleResult();

        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Student addStudent(Student student) {

        //removed try catch PersistantException round this method
        Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email");
        query.setParameter("email", student.getEmail());
        if(!query.getResultList().isEmpty()){

            throw new ResourceCreationException("Record containing email already exist");
        }

        entityManager.persist(student);
        entityManager.flush();

        return student;
    }

    @Override
    public void removeStudent(String email) {
        //Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.email = :email");
        try{
            Student student = getStudent(email);
            entityManager.remove(student);
            entityManager.flush();

        }catch(Exception e){

            logger.info(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }


        //Native Query
        //Query query = entityManager.createNativeQuery("DELETE FROM student WHERE email = :email", Student.class);

//        query.setParameter("email", email)
//             .executeUpdate();
    }

    @Transactional
    @Override
    public Student updateStudent(Student student) {

        try{
            removeStudent(student.getEmail());
            Student updatedStudent = entityManager.merge(student);
            entityManager.flush();

            return updatedStudent;

        }catch(Exception e){
            logger.info(student + " " + e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }
    }

    @Override
    public void updateStudentPartial(Student student) {
        Student studentFound = (Student)entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email")
                .setParameter("email", student.getEmail()).getSingleResult();

        Query query = entityManager.createQuery("UPDATE Student SET forename = :studentForename WHERE email = :email");
        query.setParameter("studentForename", student.getFirstName())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }
}
