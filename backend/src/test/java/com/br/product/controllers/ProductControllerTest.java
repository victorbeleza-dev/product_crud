package com.br.product.controllers;

import com.br.product.models.Product;
import com.br.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", 10.0));
        products.add(new Product(2L, "Product 2", 20.0));

        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertEquals(2, result.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById_withExistingId_shouldReturnProduct() {
        Product product = new Product(1L, "Product 1", 10.0);

        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void getProductById_withNonExistingId_shouldReturnNotFound() {
        when(productService.getProductById(1L)).thenReturn(null);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        Product product = Product.builder().name("Product 1").price(10.0).build();
        Product createdProduct = new Product(1L, "Product 1", 10.0);

        when(productService.createProduct(product)).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
        verify(productService, times(1)).createProduct(product);
    }

    @Test
    void updateProduct_withExistingId_shouldReturnUpdatedProduct() {
        Product updatedProduct = new Product(1L, "Product 1 Updated", 20.0);

        when(productService.updateProduct(1L, updatedProduct)).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProduct, response.getBody());
        verify(productService, times(1)).updateProduct(1L, updatedProduct);
    }

    @Test
    void updateProduct_withNonExistingId_shouldReturnNotFound() {
        Product product = new Product(1L, "Product 1", 10.0);

        when(productService.updateProduct(1L, product)).thenReturn(null);

        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).updateProduct(1L, product);
    }

    @Test
    void deleteProduct_withExistingId_shouldReturnNoContent() {
        when(productService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void deleteProduct_withNonExistingId_shouldReturnNotFound() {
        when(productService.deleteProduct(1L)).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
