package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

public interface TeacherTransactionAccess {

    Teacher addTeacher(Teacher teacher) throws Exception;

    Teacher getTeacherByEmail(String email) throws Exception;
}
