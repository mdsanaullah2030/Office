package com.saverfavor.capnest.service;

import com.saverfavor.capnest.entity.Product;
import com.saverfavor.capnest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save product: " + e.getMessage(), e);
        }
    }

}
