package com.g4bor.payment.integration.acquire_service.exception;

import com.g4bor.payment.database.exception.EntityNotFoundException;
import com.g4bor.payment.database.exception.InsufficientFundsException;
import com.g4bor.payment.integration.acquire_service.model.MessageResponse;
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

    @ExceptionHandler({RecordNotFoundException.class, EntityNotFoundException.class, NoSuchElementException.class})
    public final ResponseEntity<Object> handleNotFoundExceptions(
            RecordNotFoundException err, WebRequest request) {
        List<String> details = List.of(err.getLocalizedMessage());
        MessageResponse error = new MessageResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class, InsufficientFundsException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions(
            IllegalArgumentException err, WebRequest request) {
        List<String> details = List.of(err.getLocalizedMessage());
        MessageResponse error = new MessageResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
