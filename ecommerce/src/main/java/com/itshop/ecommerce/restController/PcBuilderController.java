package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.service.PcForPartAddService;
import com.itshop.ecommerce.service.PcBuilderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@AllArgsConstructor

public class PcBuilderController {

    @Autowired
    private PcBuilderService pcBuilderService;


    @PostMapping("/api/PcBuilder/save")
    public ResponseEntity<String> savePcBuilder(
            @RequestPart(value = "pcbuilder") PcBuilder pcBuilder,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        pcBuilderService.savePcBuilder(pcBuilder, file);

        return new ResponseEntity<>("product added succesfully with image", HttpStatus.OK);

    }






//    @GetMapping("/api/PcBuilder/get")
//    public ResponseEntity<?> getAllOrders() {
//        try {
//            List<PcBuilder> orders = pcBuilderService.getAllPcBuilder();
//            return ResponseEntity.ok(orders);
//        } catch (Exception e) {
//            e.printStackTrace(); // See detailed error in logs
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error fetching PcBuilder data: " + e.getMessage());
//        }
//    }



    @GetMapping("/api/PcBuilder/Allget")
    public ResponseEntity<List<PcBuilder>> getAllLocation() {
        List<PcBuilder> pcBuilder = pcBuilderService.getAllPcBuilder();
        return ResponseEntity.ok(pcBuilder);}




        @GetMapping("/api/PcBuilder/get/{id}")
    public Optional<PcBuilder> getPcBuilderById(@PathVariable int id) {
        return pcBuilderService.getPcBuilderById(id);
    }

}
