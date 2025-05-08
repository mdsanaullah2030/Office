package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.ProductDetailsService;
import com.itshop.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;



    @PostMapping("/api/Product/save")
    public ResponseEntity<String> saveProduct(
            @RequestPart(value = "product") Product product,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        productService.saveProduct(product, file);

        return new ResponseEntity<>("product added succesfully with image", HttpStatus.OK);

    }




    @GetMapping("/api/Product/getall")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/api/Product/get/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }


    @PutMapping("/api/Product/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }



    @GetMapping("/api/catagorybyproduct/id")
    public ResponseEntity<List<Product>> getProductByCatagoryId(@RequestParam int catagoryId) {
        List<Product> products= productService.findProductsByCatagoryID(catagoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/api/catagorybyproduct/name")
    public ResponseEntity<List<Product>> findProductByCatagoryName(@RequestParam("catagoryName") String catagoryName) {
        List<Product> products = productService.findProductsByCatagory(catagoryName);
        return ResponseEntity.ok(products);
    }




}