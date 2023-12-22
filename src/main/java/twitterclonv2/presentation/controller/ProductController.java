package twitterclonv2.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitterclonv2.domain.entity.ProductEntity;
import twitterclonv2.persistence.ProductRepository;

import java.util.List;

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
}
