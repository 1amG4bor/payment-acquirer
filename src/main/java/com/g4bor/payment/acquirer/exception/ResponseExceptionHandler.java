package com.g4bor.payment.acquirer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(
            RecordNotFoundException err, WebRequest request) {
        List<String> details = List.of(err.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Bad request", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<Object> handleNoSuchElementException(
            NoSuchElementException err, WebRequest request) {
        List<String> details = List.of(err.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Bad request", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException err, WebRequest request) {
        List<String> details = List.of(err.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Bad request", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}