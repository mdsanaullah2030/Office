package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllCamera;
import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.DesktopPcAll;
import com.itshop.ecommerce.service.AllLaptopService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@AllArgsConstructor
@RestController
@CrossOrigin
public class AllLaptopController {
    @Autowired
    private AllLaptopService allLaptopService;

    @GetMapping("/api/allLaptop/getall")
    public List<AllLaptop> getAllNoteBooks() {
        return allLaptopService.getAllProductDetails();
    }



    @GetMapping("/api/AllLaptop/{id}")
    public AllLaptop getProductBy(@PathVariable int id) {
        return allLaptopService.getAllLaptopById(id);
    }



    @PostMapping("/api/allLaptop/save")
    public ResponseEntity<String> saveallLaptop(
            @RequestPart("allLaptop") AllLaptop allLaptop,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3

    )   throws IOException {
        allLaptopService.saveallLaptop(allLaptop, image1, image2,image3);
        return new ResponseEntity<>("allLaptop saved successfully with images", HttpStatus.OK);
    }






    @PutMapping("/api/AllLaptop/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestPart("allLaptop") AllLaptop updatedItem,
            @RequestParam(value = "imagea", required = false) MultipartFile image1File,
            @RequestParam(value = "imageb", required = false) MultipartFile image2File,
            @RequestParam(value = "imagec", required = false) MultipartFile image3File
    ) throws IOException {
        allLaptopService.updateAllLaptop(id, updatedItem, image1File, image2File, image3File);
        return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
    }

//


    @DeleteMapping("/api/AllLaptop/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            allLaptopService.deleteAlLLaptop(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }











    //Filter
    @GetMapping("/api/alllaptop/filter")
    public ResponseEntity<List<AllLaptop>> filterAllLaptop(
            @RequestParam(required = false) String generation,
            @RequestParam(required = false) String processortype,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) String displaysizerange,
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String graphicsmemory,
            @RequestParam(required = false) String operatingsystem,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String weightrange,
            @RequestParam(required = false) String fingerprintsensor,
            @RequestParam(required = false) String lan,
            @RequestParam(required = false) String graphicschipset,
            @RequestParam(required = false) String maxramsupport,
            @RequestParam(required = false) String touchscreen,
            @RequestParam(required = false) String displayresolutionrange,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName,
            @RequestParam(required = false) Double regularprice
    ) {
        List<AllLaptop> filteredLaptops = allLaptopService.filterAllLaptop(
                generation, processortype, warranty, displaysizerange, ram, graphicsmemory,
                operatingsystem, color, weightrange, fingerprintsensor, lan, graphicschipset,
                maxramsupport, touchscreen, displayresolutionrange, catagoryName, productName,
                brandName, productItemName, regularprice
        );
        return ResponseEntity.ok(filteredLaptops);
    }

}
