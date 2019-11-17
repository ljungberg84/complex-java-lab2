package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class TeacherTransaction implements TeacherTransactionAccess {


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
            Query query = entityManager.createQuery("SELECT s FROM Teacher s WHERE s.email = :email");
            query.setParameter("email", teacher.getEmail());
            if(!query.getResultList().isEmpty()){

                throw new ResourceCreationException(String.format("Teacher with email: %s already exist", teacher.getEmail()));
            }

            return entityManager.merge(teacher);
        }catch(Exception e){
            throw new ResourceCreationException(String.format("Error adding teacher: %s , &s", teacher.getEmail(),  e.getMessage()));
        }

    }

    @Override
    public Teacher updateTeacher(Teacher teacher) throws Exception {

        try{
            Teacher updatedTeacher =  entityManager.merge(teacher);
            entityManager.flush();

            return updatedTeacher;
        }catch(Exception e){
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

            throw new ResourceNotFoundException(String.format("Teacher with email: %s not found", email));
        }



    }
}
