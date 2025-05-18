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



    @GetMapping("/api/productDetails/byName/{name}")
    public ResponseEntity<ProductDetails> getProductDetailsByName(@PathVariable String name) {
        return productDetailsService.getProductDetailsByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @GetMapping("/api/productDetails/{id}")
    public ProductDetails getProductById(@PathVariable int id) {
        return productDetailsService.getProductById(id);
    }


    @GetMapping("/api/productDetails/byCategory/{catagoryId}")
    public ResponseEntity<List<ProductDetails>> getProductDetailsByCatagoryId(@PathVariable int catagoryId) {
        List<ProductDetails> productList = productDetailsService.getProductDetailsByCatagoryId(catagoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Brand By ID Get All Product Details

    @GetMapping("/api/productDetails/Brand/get/ById/{id}")
    public ResponseEntity<List<ProductDetails>> getProductDetailsByBrandId(@PathVariable("id") int brandId) {
        List<ProductDetails> productList = productDetailsService.getProductDetailsByBrandId(brandId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }






    @PostMapping("/api/ProductDetails/save")
    public ResponseEntity<String> saveProductDetails(
            @RequestPart("productDetails") ProductDetails productDetails,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3

    )   throws IOException {
        productDetailsService.saveProductDetails(productDetails, image1, image2,image3);
        return new ResponseEntity<>("ProductDetails saved successfully with images", HttpStatus.OK);
    }






    @GetMapping("/api/productDetails/filter")
    public ResponseEntity<List<ProductDetails>> filterProductDetails(
            @RequestParam(required = false) String brandname,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double regularPrice
    ) {
        List<ProductDetails> results = productDetailsService.filterProductDetails(brandname, productName, regularPrice);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


}
