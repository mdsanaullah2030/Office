package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService noteBookService;

    @PostMapping("/api/Product/save")
    public Product saveNoteBook(@RequestBody Product noteBook) {
        return noteBookService.saveNoteBook(noteBook);
    }

    @GetMapping("/api/Product/getall")
    public List<Product> getAllNoteBooks() {
        return noteBookService.getAllNoteBooks();
    }

    @GetMapping("/api/Product/get/{id}")
    public Product getNoteBookById(@PathVariable int id) {
        return noteBookService.getNoteBookById(id);
    }


    @PutMapping("/api/Product/update/{id}")
    public Product updateNoteBook(@PathVariable int id, @RequestBody Product updatedProduct) {
        return noteBookService.updateNoteBook(id, updatedProduct);
    }

}
