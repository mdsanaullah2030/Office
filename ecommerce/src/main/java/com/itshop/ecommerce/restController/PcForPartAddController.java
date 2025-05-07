package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.service.PcForPartAddService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Repository
@RestController
@AllArgsConstructor
@CrossOrigin
public class PcForPartAddController {

    @Autowired
    private PcForPartAddService cpuAddService;



    @PostMapping("/api/PcForPartAdd/save")
    public PcForPartAdd cpuAdd(@RequestBody PcForPartAdd cpuAdd){
        return cpuAddService.savePcForPart(cpuAdd);
    }
    //  Read all CPUs
    @GetMapping("/api/PcForPartAdd/get")
    public ResponseEntity<List<PcForPartAdd>> getAllCpus() {
        return ResponseEntity.ok(cpuAddService.getAllCpu());
    }


    @GetMapping("/api/PcForPartAdd/get/{id}")
    public Optional<PcForPartAdd> getCpuById(@PathVariable int id) {
        return cpuAddService.getCpuById(id);
    }


    @PutMapping("/api/PcForPartAdd/update/{id}")
    public ResponseEntity<PcForPartAdd> updateCpu(@PathVariable int id, @RequestBody PcForPartAdd cpuAdd) {
        PcForPartAdd updatedCpu = cpuAddService.updatePcForPartAdd(id, cpuAdd);
        return ResponseEntity.ok(updatedCpu);
    }


    //  Delete CPU by ID
    @DeleteMapping("/api/PcForPartAdd/delete/{id}")
    public ResponseEntity<String> deleteCpu(@PathVariable int id) {
        cpuAddService.deletePcForPart(id);
        return ResponseEntity.ok("Deleted CPU with ID " + id);
    }



}
