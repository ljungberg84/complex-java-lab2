package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import java.util.List;

public interface SubjectTransactionAccess {

    List listAllSubjects();
    Subject addSubject(Subject subject);
    List<Subject> getSubjectByName(List<String> subject);
}
