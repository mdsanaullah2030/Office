package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Product;
import com.ecommerce.brandlyandco.service.ProductService;
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








//Filter



}
