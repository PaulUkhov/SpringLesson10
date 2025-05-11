package org.example.serviceproduct.controller;

import org.example.serviceproduct.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody Map<String, Object> request) {
        try {
            Long productId = Long.valueOf(request.get("productId").toString());
            BigDecimal amount = new BigDecimal(request.get("amount").toString());

            // Предположим, что перед оплатой должна быть проверка резерва
            if (!paymentService.isReserved(productId)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Product not reserved"));
            }

            paymentService.charge("1234567812345678", amount); // карта захардкожена для примера
            return ResponseEntity.ok(Map.of("status", "paid"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
