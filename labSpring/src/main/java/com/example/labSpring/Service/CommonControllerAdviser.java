package com.example.labSpring.Service;

import lombok.extern.slf4j.Slf4j;
import com.example.labSpring.model.Exception.ScadaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class CommonControllerAdviser {
    @ExceptionHandler(ScadaException.class)
    public ResponseEntity<Void> handleScadaException(ScadaException e){
        log.warn("Exception occurred ",e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e){
        log.error("Unhandled Exception occurred ",e);
        return ResponseEntity.internalServerError().build();
    }
}
