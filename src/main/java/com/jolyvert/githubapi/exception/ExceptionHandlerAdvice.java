package com.jolyvert.githubapi.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(UserNotFoundException e) {
        return ResponseEntity.status(e.getStatus())
                .body(Map.of(
                        "status", e.getStatus(),
                        "message", e.getMessage()
                ));
    }
}
