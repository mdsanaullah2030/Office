package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.service.CatagoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class CatagoryController {

    @Autowired
    private CatagoryService catagoryService;

    @PostMapping("/api/catagories/save")
    public Catagory createCatagory(@RequestBody Catagory catagory) {
        return catagoryService.saveCatagory(catagory);
    }

    @GetMapping("/api/catagories/get")
    public List<Catagory> getAllCatagories() {
        return catagoryService.getAllCatagories();
    }

    @GetMapping("/api/catagories/get/{id}")
    public Optional<Catagory> getCatagoryById(@PathVariable int id) {
        return catagoryService.getCatagoryById(id);
    }

    @PutMapping("/api/catagories/update/{id}")
    public Catagory updateCatagory(@PathVariable int id, @RequestBody Catagory catagory) {
        return catagoryService.updateCatagory(id, catagory);
    }





    @GetMapping("/api/findCatagories/name")
    public ResponseEntity<List<Catagory>>findCatagoryName(@RequestParam(value = "catagoriName")String catagoriName){
        List<Catagory> catagories=catagoryService.findCatagoriesByName(catagoriName);
        return  ResponseEntity.ok(catagories);
    }
}
