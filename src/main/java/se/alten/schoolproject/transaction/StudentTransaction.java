package se.alten.schoolproject.transaction;

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
import java.util.logging.Logger;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess{

    private static final Logger logger = Logger.getLogger("StudentTransaction");

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;


    @Override
    //@NamedEntityGraph()
    //@EntityGraph(value = "Item.characteristics")
    public List<Student> listAllStudents() {

//        Query query = entityManager.createQuery("SELECT s from Student s JOIN FETCH s.subjectObjs t");
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

            logger.info(e.getMessage());
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

            logger.info(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    @Override
    @Transactional(REQUIRED)
    public Student updateStudent(Student student) throws Exception{

        try{
            //Student studentToUpdate = getStudent(student.getEmail());
            //Student studentToUpdate = new Student();
            //studentToUpdate.setFirstName(student.getFirstName());
            //studentToUpdate.setLastName(student.getLastName());
            //studentToUpdate.setSubjectObjs(student.getSubjectObjs());

            //Student updatedStudent = entityManager.merge(studentToUpdate);

            logger.info("---------------------------------");
            logger.info("student before database: " + student);
            logger.info("---------------------------------");

            Student updatedStudent = entityManager.merge(student);
            logger.info("---------------------------------");
            logger.info("student after database: " + student);
            logger.info("---------------------------------");
            entityManager.flush();

            return updatedStudent;

        }catch(Exception e){

            logger.info(student + " " + e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }
    }
}
