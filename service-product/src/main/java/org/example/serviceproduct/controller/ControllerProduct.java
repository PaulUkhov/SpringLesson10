package org.example.serviceproduct.controller;

import lombok.RequiredArgsConstructor;
import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.example.serviceproduct.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ControllerProduct {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        return productService.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        //2 Вариант
//        Optional<Product> product = productRepository.findById(id);
//        if (product.isPresent()) {
//            return new ResponseEntity<>(product.get(), HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
//        }
        //3 Вариант
//        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>((HttpStatus.NOT_FOUND)));


    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> save(@PathVariable Long id, @RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(productService.save(product));


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
