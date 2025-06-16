package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllCamera;
import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllPrinter;
import com.itshop.ecommerce.entity.DesktopPcAll;
import com.itshop.ecommerce.service.AllCameraService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping
public class AllCameraController {

    private final AllCameraService allCameraService;


    @GetMapping("/api/allcamera/getall")
    public List<AllCamera> getAll() {
        return allCameraService.getAllCamera();
    }


    @GetMapping("/api/AllCamera/{id}")
    public AllCamera getProductBy(@PathVariable int id) {
        return allCameraService.getAllCameraById(id);
    }





    @PostMapping("/api/allcamera/save")
    public ResponseEntity<String> saveAllCamera(
            @RequestPart("allCamera") AllCamera allCamera,
            @RequestPart(value = "imagea", required = true) MultipartFile image1,
            @RequestPart(value = "imageb", required = true) MultipartFile image2,
            @RequestPart(value = "imagec", required = true) MultipartFile image3
    ) throws IOException {
        allCameraService.saveAllCamera(allCamera, image1, image2, image3);
        return new ResponseEntity<>("AllCamera saved successfully", HttpStatus.OK);
    }




    @PutMapping("/api/AllCamera/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestPart("allCamera") AllCamera updatedItem,
            @RequestParam(value = "imagea", required = false) MultipartFile image1File,
            @RequestParam(value = "imageb", required = false) MultipartFile image2File,
            @RequestParam(value = "imagec", required = false) MultipartFile image3File
    ) throws IOException {
        allCameraService.updateAllCamera(id, updatedItem, image1File, image2File, image3File);
        return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
    }




    @DeleteMapping("/api/AllCamera/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            allCameraService.deleteAllCamera(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    //Filter
    @GetMapping("/api/allcamera/filter")
    public ResponseEntity<List<AllCamera>> filterAllCamera(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String totalpixel,
            @RequestParam(required = false) String displaysize,
            @RequestParam(required = false) String digitalzoom,
            @RequestParam(required = false) String opticalzoom,
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Double specialprice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName
    ) {
        List<AllCamera> filtered = allCameraService.filterAllCamera(
                name, totalpixel, displaysize, digitalzoom, opticalzoom,
                regularprice, specialprice, warranty,
                catagoryName, brandName, productItemName
        );
        return ResponseEntity.ok(filtered);
    }




}
