package com.saverfavor.capnest.restController;

import com.saverfavor.capnest.entity.Category;
import com.saverfavor.capnest.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/api/categories/save")
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
