package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.CCBuilder;
import com.itshop.ecommerce.entity.Item;
import com.itshop.ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/api/items/get")
    public List<Item> getAllItems() {
        return itemService.getAll();
    }



    @GetMapping("/api/items/get/{id}")
    public Optional<Item> getById(@PathVariable int id) {
        return itemService.getById(id);
    }




    @PostMapping("/api/items/save")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(item));
    }

    @PutMapping("/api/items/update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item item) {
        try {
            return ResponseEntity.ok(itemService.update(id, item));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/api/items/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable int id) {
        try {
            itemService.delete(id);
            return ResponseEntity.ok("Item deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
    }
}
