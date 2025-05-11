package org.example.serviceproduct;

import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ServiceProductApplication.class, TestSecurityConfiguration.class})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class OrderFlowIntegrationTest {
    @Autowired
    private ProductRepository productRepository;
    private Long productId1;
    private Long productId2;
    private Long productId3;
    private Long productId4;

    @BeforeEach
    void setupData() {
        productId1 = productRepository.save(new Product(null, "Product 1", 100L)).getId();
        productId2 = productRepository.save(new Product(null, "Product 2", 100L)).getId();
        productId3 = productRepository.save(new Product(null, "Product 3", 100L)).getId();
        productId4 = productRepository.save(new Product(null, "Product 4", 100L)).getId();
    }

    @Autowired
    private WebTestClient webTestClient;

    // 1. Успешное резервирование и оплата
    @Test
    void testSuccessfulReserveAndPayment() {
        // резервируем товар
        webTestClient.post().uri("/warehouse/reserve")
                .bodyValue(Map.of("productId", productId1, "quantity", 2))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("reserved");

        // оплачиваем
        webTestClient.post().uri("/payment/pay")
                .bodyValue(Map.of("productId", productId1, "amount", 200.0))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("paid");
    }

    // 2. Ошибка оплаты без резервирования
    @Test
    void testPaymentWithoutReservationFails() {
        webTestClient.post().uri("/payment/pay")
                .bodyValue(Map.of("productId", 2, "amount", 100.0))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").exists();
    }

    // 3. Ошибка резервирования при недостатке товара
    @Test
    void testReserveWithInsufficientStockFails() {
        webTestClient.post().uri("/warehouse/reserve")
                .bodyValue(Map.of("productId", 3, "quantity", 9999))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").value(Matchers.containsString("Not enough stock"));
    }

    // 4. Повторное резервирование приводит к ошибке
    @Test
    void testDoubleReservationFails() {
        // первый раз — успешно
        webTestClient.post().uri("/warehouse/reserve")
                .bodyValue(Map.of("productId", 4, "quantity", 1))
                .exchange()
                .expectStatus().isOk();

        // второй раз — ошибка (уже зарезервировано или нехватка)
        webTestClient.post().uri("/warehouse/reserve")
                .bodyValue(Map.of("productId", 4, "quantity", 1))
                .exchange()
                .expectStatus().isBadRequest();
    }

}
