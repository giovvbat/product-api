package com.giovanna.demo.infra.handler.spring;

import com.giovanna.demo.dto.error.RestErrorRecordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLSyntaxErrorException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestDefaultExceptionHandler {
    /*spring annotations validations*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /*failed sql-queries*/
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<RestErrorRecordDto> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorRecordDto(HttpStatus.BAD_REQUEST.toString(), "no such information stored in database!"));
    }

    /*wrong data-type provided*/
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.toString());
        errors.put(ex.getName(), "data-type received does not match the expected");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /*wrong data-type provided*/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorRecordDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorRecordDto(HttpStatus.BAD_REQUEST.toString(), "unreadable data-entry for required fields"));
    }

    /*no endpoint that matches the available*/
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestErrorRecordDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorRecordDto(HttpStatus.NOT_FOUND.toString(), "resource not found"));
    }
}
