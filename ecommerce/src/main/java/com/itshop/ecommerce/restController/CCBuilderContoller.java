package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AboutUs;
import com.itshop.ecommerce.entity.CCBuilder;
import com.itshop.ecommerce.service.CCBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class CCBuilderContoller {

    @Autowired
    private CCBuilderService service;


    @PostMapping("/api/ccbuilder/save")
    public CCBuilder create(@RequestBody CCBuilder ccBuilder) {
        return service.create(ccBuilder);
    }

    @GetMapping("/api/ccbuilder/get")
    public List<CCBuilder> getAll() {
        return service.getAll();
    }

    @GetMapping("/api/ccbuilder/get/{id}")
    public Optional<CCBuilder> getById(@PathVariable int id) {
        return service.getById(id);
    }



    @PutMapping("/api/ccbuilder/updete/data/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody CCBuilder ccBuilder) {
        try {
            CCBuilder updated = service.update(id, ccBuilder);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }





    @DeleteMapping("/api/ccbuilder/delete/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }



}
