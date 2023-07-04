package com.br.product.services;

import com.br.product.kafka.KafkaProducer;
import com.br.product.models.Product;
import com.br.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final String NEW_PRODUCT_CREATED = "Novo produto adicionado: ";

    @Autowired
    private ProductRepository productRepository;
    private KafkaProducer kafkaProducer;

    @Autowired
    public ProductService(ProductRepository productRepository, KafkaProducer kafkaProducer) {
        this.productRepository = productRepository;
        this.kafkaProducer = kafkaProducer;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Product createProduct(Product product) {
        kafkaProducer.sendMessage(NEW_PRODUCT_CREATED + product.getName());

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalExistingProduct = productRepository.findById(id);
        if (optionalExistingProduct.isPresent()) {
            Product existingProduct = optionalExistingProduct.get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productRepository.delete(product);
            return true;
        }
        return false;
    }
}