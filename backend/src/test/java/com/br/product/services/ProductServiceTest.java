package com.br.product.services;

import com.br.product.models.Product;
import com.br.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder().name("Product 1").price(10.0).build());
        products.add(Product.builder().name("Product 2").price(20.0).build());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_withExistingId_shouldReturnProduct() {
        Product product = Product.builder().name("Product 1").price(10.0).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_withNonExistingId_shouldReturnNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(1L);

        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        Product product = Product.builder().name("Product 1").price(10.0).build();
        Product createdProduct = new Product(1L, "Product 1", 10.0);

        when(productRepository.save(product)).thenReturn(createdProduct);

        Product result = productService.createProduct(product);

        assertEquals(createdProduct, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_withExistingId_shouldReturnUpdatedProduct() {
        Product existingProduct = Product.builder().id(1L).name("Product 1").price(10.0).build();
        Product updatedProduct = Product.builder().id(1L).name("Product Updated").price(20.0).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void updateProduct_withNonExistingId_shouldReturnNull() {
        Product product = Product.builder().id(1L).name("Product 1").price(10.0).build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.updateProduct(1L, product);

        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(product);
    }

    @Test
    void deleteProduct_withExistingId_shouldReturnTrue() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product.builder().id(1L).name("Product 1").price(10.0).build()));

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void deleteProduct_withNonExistingId_shouldReturnFalse() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = productService.deleteProduct(1L);

        assertFalse(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).delete(any(Product.class));
    }
}