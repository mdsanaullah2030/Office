package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.ProductItem;
import com.itshop.ecommerce.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {

    @Autowired
    private ProductItemRepository repository;
    @Autowired
    private ProductItemRepository productItemRepository;

    public ProductItem save(ProductItem item) {
        return repository.save(item);
    }

    public List<ProductItem> getAll() {
        return repository.findAll();
    }

    public ProductItem getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductItem not found with id: " + id));
    }

    public ProductItem update(int id, ProductItem updatedItem) {
        ProductItem existing = getById(id);
        existing.setProductitemname(updatedItem.getProductitemname());
        existing.setCatagory(updatedItem.getCatagory());
        existing.setProduct(updatedItem.getProduct());
        existing.setProductitemname(updatedItem.getProductitemname());
        return repository.save(existing);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

// Get By get Items By Product Id
    public List<ProductItem> getItemsByProductId(int productId) {
        return productItemRepository.getItemsByProduct(productId);
    }



}
