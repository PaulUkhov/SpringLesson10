package org.example.serviceproduct;

import org.example.serviceproduct.domain.Product;
import org.example.serviceproduct.repository.ProductRepository;
import org.example.serviceproduct.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

//Модульный тест
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should Return Product By Id")
    void ShouldReturnProductById() {
        Long productId = 1L;
        Product expectedProduct = new Product(productId, "apple", 25L);
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> actualProduct = productService.findById(productId);

        Assertions.assertEquals(expectedProduct, actualProduct.isPresent() ? expectedProduct : null);
        //Вызвали ли репозиторий
        verify(productRepository, times(1)).findById(productId);
    }
}