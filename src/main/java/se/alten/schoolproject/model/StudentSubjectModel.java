//package se.alten.schoolproject.model;
//
//import lombok.*;
//import se.alten.schoolproject.entity.Subject;
//
//import java.io.Serializable;
//
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//public class StudentSubjectModel  implements Serializable {
//
//    private String title;
//    private PersonModel teacher;
//
//
//    public StudentSubjectModel(Subject subject) {
//
//        this.title = subject.getTitle();
//        if(subject.getTeacher() != null){
//            this.teacher = new PersonModel(subject.getTeacher());
//        }
//    }
//}
