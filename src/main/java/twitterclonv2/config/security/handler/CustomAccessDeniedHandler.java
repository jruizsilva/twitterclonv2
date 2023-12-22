package twitterclonv2.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import twitterclonv2.presentation.advice.ApiError;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(accessDeniedException.getLocalizedMessage())
                                    .message("Acceso denegado, No tienes los permisos necesarios para acceder a esta función. Por favor, contacta al administrador si crees que esto es un error.")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apiErrorJson = objectMapper.writeValueAsString(apiError);
        response.getWriter()
                .write(apiErrorJson);
    }
}
