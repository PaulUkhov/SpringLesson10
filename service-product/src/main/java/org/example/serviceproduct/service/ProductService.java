package org.example.serviceproduct.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        log.info("Find product by id: {}", id);
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        log.info("Save product: {}", product);
        return productRepository.save(product);
    }

    public Product update(Product product) {
        log.info("Update product: {}", product);
        return productRepository.findById(product.getId())
                .map(existing -> {
                    existing.setName(product.getName());
                    existing.setPrice(product.getPrice());
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + product.getId()));
    }

    public void deleteById(Long id) {
        log.info("Delete product by id: {}", id);
        productRepository.deleteById(id);
    }


//        if (productOptional.isPresent()) {
//            Product existingProduct = productOptional.get();
//            existingProduct.setName(product.getName());
//            existingProduct.setPrice(product.getPrice());
//            return productRepository.save(product);
//        } else {
//            throw new EntityNotFoundException("Product not found" + product.getId());
//        }


}
