package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/api/Product/save")
    public Product saveNoteBook(@RequestBody Product noteBook) {
        return productService.saveNoteBook(noteBook);
    }

    @GetMapping("/api/Product/getall")
    public List<Product> getAllNoteBooks() {
        return productService.getAllNoteBooks();
    }

    @GetMapping("/api/Product/get/{id}")
    public Product getNoteBookById(@PathVariable int id) {
        return productService.getNoteBookById(id);
    }


    @PutMapping("/api/Product/update/{id}")
    public Product updateNoteBook(@PathVariable int id, @RequestBody Product updatedProduct) {
        return productService.updateNoteBook(id, updatedProduct);
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
