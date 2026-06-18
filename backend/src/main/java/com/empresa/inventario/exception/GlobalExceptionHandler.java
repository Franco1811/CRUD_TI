package com.empresa.inventario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Con @RestControllerAdvice creamos un interceptor global de errores.
// Si algún controlador o servicio lanza una excepción, esta clase la captura automáticamente y
// responde con un JSON ordenado en lugar de romper la app o mostrar una pantalla fea de error.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja los errores de validación de Spring (ej. @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // Maneja nuestras excepciones de lógica de negocio (ej. Número de serie
    // duplicado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarExcepcionesDeNegocio(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier otro error inesperado (Error 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErroresGenerales(Exception ex) {
        return new ResponseEntity<>("Ocurrió un error interno en el servidor: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
