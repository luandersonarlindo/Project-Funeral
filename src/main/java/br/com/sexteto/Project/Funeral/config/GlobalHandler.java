package br.com.sexteto.Project.Funeral.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.sexteto.Project.Funeral.exception.ConflictException;
import br.com.sexteto.Project.Funeral.exception.ExceptionResponse;
import br.com.sexteto.Project.Funeral.exception.NotFoundException;

/**
 * Classe responsável por capturar e tratar exceções de forma global no projeto.
 * Utiliza @RestControllerAdvice para interceptar erros lançados nos
 * controllers.
 */
@RestControllerAdvice
public class GlobalHandler extends RuntimeException {

    /**
     * Trata exceções de entidades não encontradas (404).
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException ex) {
        var exception = new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.name());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    /**
     * Trata exceções geradas por validações com @Valid (400).
     * Retorna um mapa com os campos inválidos e as mensagens de erro.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Trata exceções de conflito, como duplicidade de dados (409).
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Trata exceções genéricas e inesperadas (500).
     * Evita expor mensagens internas ao cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Um erro inesperado ocorreu!");
    }
}
