package eetac.dsa.flota.web;

import eetac.dsa.flota.excepcion.GestorConflictException;
import eetac.dsa.flota.excepcion.GestorException;
import eetac.dsa.flota.excepcion.GestorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestorFlotaExceptionHandler {

    @ExceptionHandler(GestorNotFoundException.class)
    public ResponseEntity<String> noEncontrado(GestorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(GestorConflictException.class)
    public ResponseEntity<String> conflicto(GestorConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(GestorException.class)
    public ResponseEntity<String> peticionInvalida(GestorException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
