package org.example.serviceproduct.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WarehouseService {

    // Здесь имитируем базу данных
    private final Map<Long, Integer> inventory = new HashMap<>();

    public WarehouseService() {
        // Для примера инициализируем склад с товарами
        inventory.put(1L, 100);  // товар с ID 1, 100 штук на складе
        inventory.put(2L, 50);   // товар с ID 2, 50 штук на складе
    }

    // Метод резервирования товара
    public void reserve(Long productId, int quantity) {
        Integer stock = inventory.get(productId);
        if (stock == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (stock < quantity) {
            throw new IllegalStateException("Not enough stock");
        }
        // Резервируем товар
        inventory.put(productId, stock - quantity);
    }

    // Для проверки оставшегося количества на складе
    public int getStock(Long productId) {
        return inventory.getOrDefault(productId, 0);
    }
}
