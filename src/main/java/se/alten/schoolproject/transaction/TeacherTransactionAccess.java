package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import java.util.List;

public interface TeacherTransactionAccess {

    List<Teacher> listAllTeachers() throws Exception;

    Teacher updateTeacher(Teacher teacher) throws Exception;

    Teacher addTeacher(Teacher teacher) throws Exception;

    Teacher getTeacherByEmail(String email) throws Exception;
}
