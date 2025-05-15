package com.itshop.ecommerce.restController;
import com.itshop.ecommerce.entity.ServiceFeature;
import com.itshop.ecommerce.service.ServiceFeatureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController

@CrossOrigin(origins = "*")
public class ServiceFeatureController {

    @Autowired
    private ServiceFeatureService service;

    @PostMapping("/api/ServiceFeature/save")
    public ServiceFeature createFeature(@RequestBody ServiceFeature feature) {
        return service.saveFeature(feature);
    }

    @GetMapping("/api/ServiceFeature/get")
    public List<ServiceFeature> getAllFeatures() {
        return service.getAllFeatures();
    }






    @PutMapping("/api/ServiceFeature/updete/{id}")
    public ServiceFeature updateFeature(@PathVariable int id, @RequestBody ServiceFeature updatedFeature) {
        return service.updateFeature(id, updatedFeature);
    }

    @DeleteMapping("/api/ServiceFeature/delete/{id}")
    public void deleteFeature(@PathVariable int id) {
        service.deleteFeature(id);
    }
}
