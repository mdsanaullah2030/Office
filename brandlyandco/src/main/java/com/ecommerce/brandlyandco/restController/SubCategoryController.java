package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Category;
import com.ecommerce.brandlyandco.entity.SubCategory;
import com.ecommerce.brandlyandco.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin
public class SubCategoryController {
@Autowired
    private  SubCategoryService subCategoryService;


    @PostMapping("/api/subcategories/save")
    public ResponseEntity<?> create(@RequestBody SubCategory subCategory) {
        try {
            SubCategory savedSubCategory = subCategoryService.saveSubCategory(subCategory);
            return new ResponseEntity<>(savedSubCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save SubCategory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/api/subcategories/get")
    public ResponseEntity<?> getAll() {
        try {
            List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching SubCategories: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/api/subcategories/get/{id}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable int id) {
        try {
            Optional<SubCategory> categoryOptional = subCategoryService.getSubCategoryById(id);
            if (categoryOptional.isPresent()) {
                return ResponseEntity.ok(categoryOptional.get());
            } else {
                return ResponseEntity.status(404).body("Category not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving category: " + e.getMessage());
        }
    }




    @PutMapping("/api/subcategories/updete/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody SubCategory subCategory) {
        try {
            SubCategory updated = subCategoryService.updateSubCategory(id, subCategory);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating SubCategory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/api/subcategories/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return new ResponseEntity<>("Deleted SubCategory with ID " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting SubCategory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
