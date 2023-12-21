package twitterclonv2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.entity.ProductEntity;
import twitterclonv2.repository.ProductRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> findAll() {
        List<ProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createOne(@RequestBody @Valid ProductEntity productEntity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(productRepository.save(productEntity));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception exception,
                                                                      HttpServletRequest request) {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message",
                     exception.getLocalizedMessage());
        apiError.put("timestamp",
                     new Date().toString());
        apiError.put("url",
                     request.getRequestURL()
                            .toString());
        apiError.put("http-method",
                     request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status)
                             .body(apiError);
    }
}
