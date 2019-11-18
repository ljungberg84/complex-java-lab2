package se.alten.schoolproject.transaction;

import org.apache.log4j.Logger;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess{


    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(StudentTransaction.class);


    @Override
    public List<Student> listAllStudents() {

        Query query = entityManager.createQuery("SELECT s from Student s");

        return query.getResultList();
    }


    @Override
    public Student getStudent(String email) throws Exception {

        try{
            Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email");
            query.setParameter("email", email);

            return (Student)query.getSingleResult();

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @Override
    public Student addStudent(Student student) throws Exception{
        try {
            Query query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email");
            query.setParameter("email", student.getEmail());
            if (!query.getResultList().isEmpty()) {

                throw new ResourceCreationException("Record containing email already exist");
            }

            Student addedStudent = entityManager.merge(student);
            entityManager.flush();

            return addedStudent;

        }catch(RuntimeException e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException(String.format("Could not add subject: %s", e.getMessage()));
        }
    }


    @Override
    public void removeStudent(String email) throws Exception{

        try{
            Student student = getStudent(email);
            entityManager.remove(student);
            entityManager.flush();

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @Override
    @Transactional(REQUIRED)
    public Student updateStudent(Student student) throws Exception{

        try{
            Student updatedStudent = entityManager.merge(student);
            entityManager.flush();

            return updatedStudent;

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException(e.getMessage());
        }
    }
}
