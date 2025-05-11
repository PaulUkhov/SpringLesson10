package org.example.serviceproduct;

import jakarta.persistence.EntityManager;
import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
//Интеграционный тест
@EntityScan(basePackageClasses = Product.class)
@EnableJpaRepositories(basePackageClasses = ProductRepository.class)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ProductRepositoryTest  {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("product")
            .withUsername("product")
            .withPassword("product");

    @Autowired
    private ProductRepository productRepository;

//    @DynamicPropertySource
//    static void dynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.datasource.username", postgres::getUsername);
//    }

    @Test
    void testSaveAndFindProduct() {
        Product product = new Product(null, "Orange", 27L);
        Product savedProduct = productRepository.save(product);
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        Assertions.assertTrue(foundProduct.isPresent());
        Assertions.assertEquals(foundProduct.get().getName(), product.getName());

    }



}

