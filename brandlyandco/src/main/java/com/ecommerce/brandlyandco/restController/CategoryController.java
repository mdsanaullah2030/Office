package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Category;
import com.ecommerce.brandlyandco.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/api/categories/save")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            Category savedCategory = categoryService.createCategory(category);
            return ResponseEntity.ok(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating category: " + e.getMessage());
        }
    }

    @GetMapping("/api/categories/get")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving categories: " + e.getMessage());
        }
    }

    @GetMapping("/api/categories/get/byid/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        try {
            Optional<Category> categoryOptional = categoryService.getCategoryById(id);
            if (categoryOptional.isPresent()) {
                return ResponseEntity.ok(categoryOptional.get());
            } else {
                return ResponseEntity.status(404).body("Category not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving category: " + e.getMessage());
        }
    }


    @DeleteMapping("/api/categories/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting category: " + e.getMessage());
        }
    }
}
