package se.alten.schoolproject.transaction;

import org.apache.log4j.Logger;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

public class SubjectTransaction implements SubjectTransactionAccess{


    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    //@Inject
    private Logger logger = Logger.getLogger(SubjectTransaction.class);
    //private static final Logger logger = Logger.getLogger(SubjectTransaction.class);


    @Override
    public List<Subject> listAllSubjects() {

        Query query = entityManager.createQuery("SELECT s FROM Subject s");

        return query.getResultList();
    }


    @Override
    public Subject addSubject(Subject subject) throws ResourceCreationException {
        try {
            Query query = entityManager.createQuery("SELECT s FROM Subject s WHERE s.id = :id");
            query.setParameter("id", subject.getId());

            Subject addedSubject = entityManager.merge(subject);
            entityManager.flush();

            return addedSubject;

        }catch(PersistenceException e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException(String.format("Subject with title: %s already exist, Error: %s", subject.getTitle(), e.getMessage()));

        }
        catch (Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException("Error adding subject:" + e.getMessage());
        }
    }


    @Override
    public Subject getSubjectByTitle(String title) throws Exception{

        try{
            Query query = entityManager.createQuery("SELECT s FROM Subject s WHERE s.title = :title");
            query.setParameter("title", title);

            return (Subject) query.getSingleResult();

        }catch (Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceNotFoundException(String.format("Subject not found: %s, error: %s", title, e.getMessage()));
        }
    }


    @Override
    public Subject updateSubject(Subject subject) throws Exception {
        try{
            Subject updatedSubject = entityManager.merge(subject);
            entityManager.flush();

            return updatedSubject;

        }catch(Exception e){

            logger.info(e.getMessage(), e);
            throw new ResourceCreationException("error updating subject: " + e.getMessage());
        }
    }
}
