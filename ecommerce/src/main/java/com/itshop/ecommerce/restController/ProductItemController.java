package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.ProductItem;
import com.itshop.ecommerce.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class ProductItemController {

    @Autowired
    private ProductItemService service;
    @Autowired
    private ProductItemService productItemService;

    @PostMapping("/api/product/items/save")
    public ResponseEntity<?> save(@RequestBody ProductItem item) {
        try {
            ProductItem savedItem = service.save(item);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save product item: " + e.getMessage());
        }
    }

    @GetMapping("/api/product/items/get")
    public ResponseEntity<?> getAll() {
        try {
            List<ProductItem> list = service.getAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch items: " + e.getMessage());
        }
    }

    @GetMapping("/api/product/items/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            ProductItem item = service.getById(id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch item: " + e.getMessage());
        }
    }

    @PutMapping("/api/product/items/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProductItem item) {
        try {
            ProductItem updated = service.update(id, item);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found: " + e.getMessage()); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update item: " + e.getMessage());
        }
    }



//findByProductId

    @GetMapping("/api/item/findbyproductid/get/{id}")
    public ResponseEntity<List<ProductItem>> getByProductId(@PathVariable("id") int productId) {
        List<ProductItem> items = productItemService.getItemsByProductId(productId);
        return ResponseEntity.ok(items);
    }






    @DeleteMapping("/api/product/items/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Item deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete item: " + e.getMessage());
        }
    }
}

