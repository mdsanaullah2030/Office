package com.itshop.ecommerce.service;



import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }


    public Product updateProduct(int id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setCatagory(updatedProduct.getCatagory());
            return productRepository.save(existing);
        }).orElse(null);
    }




    public List<Product> findProductsByCatagory(String catagoryName) {
        return productRepository.findProductsByCatagoryName(catagoryName);
    }


    public List<Product> findProductsByCatagoryID(int catagoryId){
        return productRepository.findProductsByCatagoryId(catagoryId);
    }



}