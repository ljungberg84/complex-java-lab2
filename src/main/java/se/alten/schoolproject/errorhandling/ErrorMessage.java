package se.alten.schoolproject.errorhandling;

import lombok.*;

import java.io.Serializable;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessage implements Serializable {

    private String message;
}
