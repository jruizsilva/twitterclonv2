package twitterclonv2.presentation.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import twitterclonv2.common.exception.ObjectNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException e,
                                                           HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Objeto no encontrado")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e,
                                                    HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Error interno del servidor,  por favor intente mas tarde")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e,
                                                         HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Acceso denegado, No tienes los permisos necesarios para acceder a esta función. Por favor, contacta al administrador si crees que esto es un error.")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                   HttpServletRequest request) {
        System.out.println("handleMethodArgumentNotValidException");
        List<String> errors = new ArrayList<>();
        if (!e.getFieldErrors()
              .isEmpty()) {
            e.getFieldErrors()
             .forEach(fieldError -> {
                 String message = String.format("error in field %s: %s",
                                                fieldError.getField(),
                                                fieldError.getDefaultMessage()
                                                          .toUpperCase());
                 errors.add(message);
             });
        }
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Error en la petición enviada")
                                    .errors(errors)
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }
}
