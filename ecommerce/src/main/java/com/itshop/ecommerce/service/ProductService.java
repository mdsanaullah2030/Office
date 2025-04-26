package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository noteBookRepository;

    public Product saveNoteBook(Product noteBook) {
        return noteBookRepository.save(noteBook);
    }

    public List<Product> getAllNoteBooks() {
        return noteBookRepository.findAll();
    }

    public Product getNoteBookById(int id) {
        return noteBookRepository.findById(id).orElse(null);
    }


    public Product updateNoteBook(int id, Product updatedProduct) {
        return noteBookRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setCatagory(updatedProduct.getCatagory());
            return noteBookRepository.save(existing);
        }).orElse(null);
    }

}
