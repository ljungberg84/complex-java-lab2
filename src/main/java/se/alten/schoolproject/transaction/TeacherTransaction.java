package se.alten.schoolproject.transaction;

import org.apache.log4j.Logger;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class TeacherTransaction implements TeacherTransactionAccess {

    private static final Logger logger = Logger.getLogger(TeacherTransaction.class);


    @PersistenceContext(unitName="school")
    private EntityManager entityManager;


    @Override
    public List<Teacher> listAllTeachers() throws Exception {

        Query query = entityManager.createQuery("SELECT s FROM Teacher s");

        return query.getResultList();
    }


    @Override
    public Teacher addTeacher(Teacher teacher) throws Exception{

        try{
            logger.info(String.format("Adding teacher: %s to db", teacher));

            Query query = entityManager.createQuery("SELECT s FROM Teacher s WHERE s.email = :email");
            query.setParameter("email", teacher.getEmail());
            if(!query.getResultList().isEmpty()){

                throw new Exception(String.format("Teacher with email: %s already exist", teacher.getEmail()));
            }
            Teacher addedTeacher = entityManager.merge(teacher);
            entityManager.flush();

            return addedTeacher;

        }catch(PersistenceException e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException(String.format("Teacher with email: %s already exist, Error: %s", teacher.getEmail(), e.getMessage()));

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException(e.getMessage());
        }
    }


    @Override
    public Teacher updateTeacher(Teacher teacher) throws Exception {

        try{
            logger.info(String.format("Updating teacher: %s", teacher));

            Teacher updatedTeacher =  entityManager.merge(teacher);
            entityManager.flush();

            return updatedTeacher;
        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException("Error updating resource: " + e.getMessage());
        }
    }


    @Override
    public Teacher getTeacherByEmail(String email) throws Exception{
        try {
            Query query = entityManager.createQuery("SELECT s FROM Teacher s WHERE s.email = :email");
            query.setParameter("email", email);

            return (Teacher) query.getSingleResult();

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceNotFoundException(String.format("Teacher with email: %s not found", email));
        }
    }
}
