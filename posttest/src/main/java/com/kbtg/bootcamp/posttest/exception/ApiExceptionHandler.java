package com.kbtg.bootcamp.posttest.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGlobalException(Exception e) {
        //logger("Exception error: {}", e.getMessage());
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new ArrayList<>(List.of(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())),
                new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> messageResponse = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> messageResponse.add(fieldError.getDefaultMessage()));

        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                messageResponse, new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                new ArrayList<>(List.of(e.getMessage())), new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<Object> handleMethodValidationException(HandlerMethodValidationException e) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                new ArrayList<>(List.of("Path variable validation failure")),
                new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                new ArrayList<>(List.of(e.getMessage())),
                new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                new ArrayList<>(List.of(e.getMessage())),
                new Date()
        );
        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
