package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.service.PcForPartAddService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
@RestController
@AllArgsConstructor
@CrossOrigin
public class PcForPartAddController {

    @Autowired
    private PcForPartAddService pcForPartAddService;

//Save

@PostMapping("/api/PcForPartAdd/save")
    public ResponseEntity<String> savePcForPart(
            @RequestPart(value = "pcforpartadd") PcForPartAdd pcForPartAdd,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        pcForPartAddService.savePcForPart(pcForPartAdd, file);

        return new ResponseEntity<>("pcForPartAdd added succesfully with image", HttpStatus.OK);

    }




    //  Read all CPUs
    @GetMapping("/api/PcForPartAdd/get")
    public ResponseEntity<List<PcForPartAdd>> getAllCpus() {
        return ResponseEntity.ok(pcForPartAddService.getAllCpu());
    }
//Get By ID

    @GetMapping("/api/PcForPartAdd/get/{id}")
    public Optional<PcForPartAdd> getCpuById(@PathVariable int id) {
        return pcForPartAddService.getCpuById(id);
    }


    @PutMapping("/api/PcForPartAdd/update/{id}")
    public ResponseEntity<PcForPartAdd> updateCpu(@PathVariable int id, @RequestBody PcForPartAdd cpuAdd) {
        PcForPartAdd updatedCpu = pcForPartAddService.updatePcForPartAdd(id, cpuAdd);
        return ResponseEntity.ok(updatedCpu);
    }


    //  Delete CPU by ID
    @DeleteMapping("/api/PcForPartAdd/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            pcForPartAddService.deleteProductDetils(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/PcForPartAdd/getPcBuilder/Byid/{id}")
    public ResponseEntity<?> getPcPartsByBuilderId(@PathVariable("id") int id) {
        try {
            List<PcForPartAdd> parts = pcForPartAddService.getPartsByPcBuilderId(id);
            return ResponseEntity.ok(parts);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred: " + ex.getMessage());
        }
    }



    @PutMapping("/api/pcforpartadd/update/{id}")
    public ResponseEntity<String> updatePcPart(
            @PathVariable int id,
            @RequestPart("pcpart") PcForPartAdd updatedPart,
            @RequestParam(value = "imagea", required = false) MultipartFile imageFile
    ) throws IOException {
        pcForPartAddService.updatePcPart(id, updatedPart, imageFile);
        return ResponseEntity.ok("PC part updated successfully");
    }





///Filter

    @GetMapping("/api/pcparts/filter")
    public ResponseEntity<List<PcForPartAdd>> filterPcParts(
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) Integer pcbuilderId
    ) {
        List<PcForPartAdd> results = pcForPartAddService.filterPcParts(regularprice, warranty, pcbuilderId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


}
