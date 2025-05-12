package org.example.serviceproduct;

import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ControllerProductTest {
    @LocalServerPort
    private int port;
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
//        webTestClient = WebTestClient.bindToServer()
//                .baseUrl("http://localhost:" + port)
//                .build();
        productRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        Product p1 = new Product(null, "Product 1", 300L);
        Product p2 = new Product(null, "Product 2", 200L);
        productRepository.saveAll(List.of(p1, p2));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(2);
    }

    @Test
    public void testFindByIdFound() {
        Product saved = productRepository.save(new Product(null, "Product 1", 100L));

        webTestClient.get()
                .uri("/api/products/{id}", saved.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .value(product -> product.getName().equals("Product 1"));
    }

    @Test
    public void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/api/products/{id}", 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testSave() {
        Product product = new Product(null, "New Product", 300L);

        webTestClient.post()
                .uri("/api/products/{id}", 1)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .value(p -> p.getName().equals("New Product"));
    }

    @Test
    public void testUpdate() {
        Product saved = productRepository.save(new Product(null, "Old Product", 100L));
        Product updated = new Product(null, "Updated Product", 150L);

        webTestClient.put()
                .uri("/api/products/{id}", saved.getId())
                .bodyValue(updated)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .value(p -> p.getName().equals("Updated Product"));
    }

    @Test
    public void testDelete() {
        Product saved = productRepository.save(new Product(null, "To Delete", 100L));

        webTestClient.delete()
                .uri("/api/products/{id}", saved.getId())
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri("/api/products/{id}", saved.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}
