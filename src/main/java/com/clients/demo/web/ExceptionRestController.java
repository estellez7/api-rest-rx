package com.clients.demo.web;

import com.clients.demo.model.payload.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

//TODO: Check
@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handleEntityNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageResponse.builder()
                .message(HttpStatus.NOT_FOUND.name())
                .object("NaN")
                .build());
    }
}
