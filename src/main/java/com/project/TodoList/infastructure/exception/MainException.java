package com.project.TodoList.infastructure.exception;

import com.project.TodoList.infastructure.enums.ExceptionsCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class MainException extends RuntimeException{
    private ExceptionsCode code;
    private HttpStatus statusCode;
}
