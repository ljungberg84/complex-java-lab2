package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.ResourceCreationException;
import se.alten.schoolproject.errorhandling.ResourceNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

public class SubjectTransaction implements SubjectTransactionAccess{

    private static final Logger logger = Logger.getLogger("StudentTransaction");

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List<Subject> listAllSubjects() {

        Query query = entityManager.createQuery("SELECT s FROM Subject s");

        return query.getResultList();
    }

    @Override
    public Subject addSubject(Subject subject) throws ResourceCreationException {

        Query query = entityManager.createQuery("SELECT s FROM Subject s WHERE s.id = :id");
        query.setParameter("id", subject.getId());
        if(!query.getResultList().isEmpty()){

            throw new ResourceCreationException(String.format("Record with id: %s already exist", subject.getId()));
        }

        logger.info("---------------------------------");
        logger.info("subject before database: " + subject);
        logger.info("---------------------------------");

        Subject addedSubject = entityManager.merge(subject);
        entityManager.flush();

        logger.info("---------------------------------");
        logger.info("subject after database: " + subject);
        logger.info("---------------------------------");

        return addedSubject;
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
}
