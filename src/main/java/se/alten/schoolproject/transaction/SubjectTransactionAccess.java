package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.errorhandling.ResourceCreationException;

import java.util.List;

public interface SubjectTransactionAccess {

    List<Subject> listAllSubjects();
    Subject addSubject(Subject subject) throws ResourceCreationException;
    Subject getSubjectByTitle(String title) throws Exception;
    Subject updateSubject(Subject subject) throws Exception;
}
