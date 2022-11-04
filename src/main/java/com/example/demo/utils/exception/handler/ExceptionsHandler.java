package com.example.demo.utils.exception.handler;

import com.example.demo.utils.exception.StudentWithGivenEmailAlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(StudentWithGivenEmailAlreadyExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StudentWithGivenEmailAlreadyExists handleStudentWithGivenEmailAlreadyExists(StudentWithGivenEmailAlreadyExists ex) {
        return ex;
    }

}
