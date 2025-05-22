package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Item;
import com.ecommerce.brandlyandco.entity.SubCategory;
import com.ecommerce.brandlyandco.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @PostMapping("/api/item/save")
    public ResponseEntity<?> saveItem(@RequestBody Item item) {
        try {
            Item savedItem = itemService.saveItem(item);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            // You can log the error if needed
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save item: " + e.getMessage());
        }
    }



    @GetMapping("/api/item/get")
    public ResponseEntity<?> getAll() {
        try {
            List<Item> subCategories = itemService.getAllSubCategories();
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching SubCategories: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/api/item/get/{id}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable int id) {
        try {
            Optional<Item> categoryOptional = itemService.getSubCategoryById(id);
            if (categoryOptional.isPresent()) {
                return ResponseEntity.ok(categoryOptional.get());
            } else {
                return ResponseEntity.status(404).body("Category not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving category: " + e.getMessage());
        }
    }




    @PutMapping("/api/item/updete/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Item subCategory) {
        try {
            Item updated = itemService.updateItem(id, subCategory);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating SubCategory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
