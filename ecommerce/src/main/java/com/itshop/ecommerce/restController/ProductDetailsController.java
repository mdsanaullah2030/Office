package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.ProductDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor

public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;




    @GetMapping("/api/productDetails/getall")
    public List<ProductDetails> getAllNoteBooks() {
        return productDetailsService.getAllProductDetails();
    }

    @PostMapping("/api/ProductDetails/save")
    public ResponseEntity<String> saveProductDetails(
            @RequestPart("productDetails") ProductDetails productDetails,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3,
            @RequestPart("imaged") MultipartFile image4,
            @RequestPart("imagef") MultipartFile image5
    )   throws IOException {
        productDetailsService.saveProductDetails(productDetails, image1, image2,image3,image4,image5);
        return new ResponseEntity<>("ProductDetails saved successfully with images", HttpStatus.OK);
    }
}
