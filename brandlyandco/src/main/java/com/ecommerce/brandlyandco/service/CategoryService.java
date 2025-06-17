package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.Category;

import com.ecommerce.brandlyandco.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }


    public Category updateCategory(int id, Category updatedCategory) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setCategoryname(updatedCategory.getCategoryname()); // Fixed typo: use `updatedCategory`
            return categoryRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Category not found with ID " + id));
    }



    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
