package org.example.serviceproduct.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class PaymentService {

    private final Set<Long> reservedProductIds = new HashSet<>();

    public void markReserved(Long productId) {
        reservedProductIds.add(productId);
    }
    public boolean isReserved(Long productId) {
        return reservedProductIds.contains(productId);
    }
    // Для простоты имитируем процесс зарядки карты
    public void charge(String cardNumber, BigDecimal amount) {
        if (cardNumber == null || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        // Имитация процесса списания средств
        System.out.println("Charging card " + cardNumber + " for amount: " + amount);
        // Логика успешной оплаты
    }
}
