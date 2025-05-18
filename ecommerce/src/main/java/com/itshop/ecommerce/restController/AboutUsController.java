package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AboutUs;
import com.itshop.ecommerce.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class AboutUsController {

    @Autowired
    private AboutUsService service;

    @PostMapping("/api/aboutus/save")
    public ResponseEntity<AboutUs> create(@RequestBody AboutUs aboutUs) {
        return ResponseEntity.ok(service.create(aboutUs));
    }

    @GetMapping("/api/aboutus/get")
    public ResponseEntity<List<AboutUs>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/api/aboutus/get/{id}")
    public ResponseEntity<AboutUs> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/aboutus/updete/{id}")
    public ResponseEntity<AboutUs> update(@PathVariable int id, @RequestBody AboutUs aboutUs) {
        return ResponseEntity.ok(service.update(id, aboutUs));
    }

    @DeleteMapping("/api/aboutus/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
