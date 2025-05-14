package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.service.PcForPartAddService;
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


    @GetMapping("api/PcBuilder/get")
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
    public ResponseEntity<String> deleteCpu(@PathVariable int id) {
        pcForPartAddService.deletePcForPart(id);
        return ResponseEntity.ok("Deleted CPU with ID " + id);
    }


    @GetMapping("/api/PcForPartAdd/getPcBuilder/Byid/{id}")
    public ResponseEntity<List<PcForPartAdd>> getPcPartsByBuilderId(@PathVariable("id") PcBuilder id) {
    List<PcForPartAdd> parts = pcForPartAddService.getPartsByPcBuilderId(id);
    return ResponseEntity.ok(parts);
}



}
