package com.br.product.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void createProduct_shouldSetValues() {
        Long id = 1L;
        String name = "Product 1";
        double price = 10.0;

        Product product = new Product(id, name, price);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
    }
}