package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.CCBuilderItemDitels;
import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import com.itshop.ecommerce.service.ProductDetailsService;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private ProductDetailsRepository productDetailsRepository;


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

///Product By ID Get All Product Details

    @GetMapping("/api/productDetails/Product/get/ById/{id}")
    public ResponseEntity<List<ProductDetails>> getProductDetailsByBrandIds(@PathVariable("id") int productId) {
        List<ProductDetails> productList = productDetailsService.getProductDetailsByProductId(productId);
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


    @PutMapping("/api/ProductDetails/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestPart("productdetails") ProductDetails updatedItem,
            @RequestParam(value = "imagea", required = false) MultipartFile image1File,
            @RequestParam(value = "imageb", required = false) MultipartFile image2File,
            @RequestParam(value = "imagec", required = false) MultipartFile image3File
    ) throws IOException {
        productDetailsService.updateProductDetails(id, updatedItem, image1File, image2File, image3File);
        return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
    }






//Delete Product Detels

    @DeleteMapping("/api/product/Ditels/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            productDetailsService.deleteProductDetils(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




//Filter

    @GetMapping("/api/productDetails/filter")
    public ResponseEntity<List<ProductDetails>> filterProductDetails(
            @RequestParam(required = false) String brandname,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Double regularPrice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) Integer productItemId
    ) {
        List<ProductDetails> results = productDetailsService.filterProductDetails(
                brandname, productName, regularPrice, warranty, productItemId
        );
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



//

    @PutMapping("/api/productDetails/publish/{id}")
    public ResponseEntity<String> publishProduct(@PathVariable int id) {
        ProductDetails product = productDetailsService.getProductById(id);
        if (product == null) return ResponseEntity.notFound().build();

        product.setPublished(true);
        productDetailsRepository.save(product);
        return ResponseEntity.ok("Product published");
    }

    @PutMapping("/api/productDetails/unpublish/{id}")
    public ResponseEntity<String> unpublishProduct(@PathVariable int id) {
        ProductDetails product = productDetailsService.getProductById(id);
        if (product == null) return ResponseEntity.notFound().build();

        product.setPublished(false);
        productDetailsRepository.save(product);
        return ResponseEntity.ok("Product unpublished");
    }

//



}
