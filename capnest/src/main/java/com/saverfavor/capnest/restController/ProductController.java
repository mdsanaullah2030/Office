package com.saverfavor.capnest.restController;

import com.saverfavor.capnest.entity.Product;
import com.saverfavor.capnest.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor

public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/products/save")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        System.out.println("Received Product: " + product);
        try {
            Product savedProduct = productService.saveProduct(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save product: " + e.getMessage());
        }
    }

}
