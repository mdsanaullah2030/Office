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


//Products data get By Item Id
    @GetMapping("/api/products/get/item/{id}")
    public ResponseEntity<List<Product>> getProductsByItemId(@RequestParam Integer itemId) {
        return ResponseEntity.ok(productService.getProductsByItemId(itemId));
    }





//Filter

    @GetMapping("/api/product/filter")
    public ResponseEntity<?> filterProducts(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Double offer,
            @RequestParam(required = false) String fabric,
            @RequestParam(required = false) Integer itemId,
            @RequestParam(required = false) Integer subCategoryId,
            @RequestParam(required = false) Integer categoryId
    ) {
        return ResponseEntity.ok(productService.filterProducts(
                color, size, regularprice, offer, fabric, itemId, subCategoryId, categoryId
        ));
    }


}
