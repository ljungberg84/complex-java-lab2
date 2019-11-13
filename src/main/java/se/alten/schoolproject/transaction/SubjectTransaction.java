package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import java.util.List;

public class SubjectTransaction implements SubjectTransactionAccess{

    @Override
    public List<Subject> listAllSubjects() {
        return null;
    }

    @Override
    public Subject addSubject(Subject subject) {
        return null;
    }

    @Override
    public List<Subject> getSubjectByName(List<String> subject) {
        return null;
    }
}
