package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Product;
import com.ecommerce.brandlyandco.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;



    @GetMapping("/api/product/getall")
    public List<Product> getAllNoteBooks() {
        return productService.getAllProduct();
    }


//

    @GetMapping("/api/product/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }



    @GetMapping("/api/product/byCategory/{categoryId}")
    public ResponseEntity<List<Product>> getProductDetailsByCategoryId(@PathVariable int categoryId) {
        List<Product> productList = productService.getProductByCatagoryId(categoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }






    @PostMapping("/api/product/save")

    public ResponseEntity<String> saveProduct(
            @RequestPart("product") Product product,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3,
             @RequestPart("imaged") MultipartFile image4

    )   throws IOException {
        productService.saveProduct(product, image1, image2,image3,image4);
        return new ResponseEntity<>("ProductDetails saved successfully with images", HttpStatus.OK);
    }


    @PutMapping("/api/Product/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestPart("productdetails") Product updatedItem,
            @RequestPart(value = "imagea", required = false) MultipartFile image1File,
            @RequestPart(value = "imageb", required = false) MultipartFile image2File,
            @RequestPart(value = "imagec", required = false) MultipartFile image3File
    ) throws IOException {
        productService.updateProduct(id, updatedItem, image1File, image2File, image3File);
        return ResponseEntity.ok("Item updated successfully");
    }


//Delete Product Detels

    @DeleteMapping("/api/product/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//Filter






}
