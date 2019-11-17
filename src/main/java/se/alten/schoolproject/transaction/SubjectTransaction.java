package se.alten.schoolproject.transaction;

import org.jboss.resteasy.logging.Logger;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class SubjectTransaction implements SubjectTransactionAccess{

    private static final Logger logger = Logger.getLogger(SubjectTransaction.class);

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;


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

            if (!query.getResultList().isEmpty()) {

                throw new ResourceCreationException(String.format("Record with id: %s already exist", subject.getId()));
            }

            Subject addedSubject = entityManager.merge(subject);
            entityManager.flush();

            return addedSubject;

        }catch (Exception e){

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

            entityManager.flush();
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

            throw new ResourceCreationException("error updating subject: " + e.getMessage());
        }
    }
}
