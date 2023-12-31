package twitterclonv2.presentation.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import twitterclonv2.common.exception.CustomObjectNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e,
                                                                  HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Incorrect username or password")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(CustomObjectNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomObjectNotFoundException(CustomObjectNotFoundException e,
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                          HttpServletRequest request) {
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

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ApiError> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e,
                                                                                 HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Incorrect username or password")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e,
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
}
