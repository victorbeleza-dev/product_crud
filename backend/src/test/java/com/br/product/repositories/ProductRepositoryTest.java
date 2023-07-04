package com.br.product.repositories;

import com.br.product.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureMockMvc
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder().name("Product 1").price(10.0).build();
        Product product2 = Product.builder().name("Product 2").price(20.0).build();
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
    }

    @Test
    void findById_withExistingId_shouldReturnProduct() {
        Long productId = 1L;

        Optional<Product> product = productRepository.findById(productId);

        assertTrue(product.isPresent());
        assertEquals(productId, product.get().getId());
    }

    @Test
    void findById_withNonExistingId_shouldReturnEmpty() {
        Long productId = 3L;

        Optional<Product> product = productRepository.findById(productId);

        assertFalse(product.isPresent());
    }

    @Test
    void save_shouldPersistProduct() {
        Product product = Product.builder().name("Product 3").price(30.0).build();

        Product savedProduct = productRepository.save(product);

        assertEquals(product, savedProduct);
        assertTrue(productRepository.findById(savedProduct.getId()).isPresent());
    }

    @Test
    void deleteById_withExistingId_shouldDeleteProduct() {
        Long productId = 1L;

        productRepository.deleteById(productId);

        assertFalse(productRepository.findById(productId).isPresent());
    }
}