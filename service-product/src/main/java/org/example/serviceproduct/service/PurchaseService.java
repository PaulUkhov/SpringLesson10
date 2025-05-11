package org.example.serviceproduct.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PurchaseService  {
    private final WarehouseService warehouseService;
    private final PaymentService paymentService;



    @Transactional
    public void purchase(Long productId, int quantity, String cardNumber) {
        warehouseService.reserve(productId, quantity);
        paymentService.charge(cardNumber, calculatePrice(productId, quantity));
    }

    private BigDecimal calculatePrice(Long productId, int quantity) {
        // допустим, фиксированная цена
        return BigDecimal.valueOf(quantity * 10L);
    }


}
