package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.HomePageImage;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.HomePageImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class HomePageImageController {

    private final HomePageImageService homePageImageService;


    @GetMapping("/api/HomePageImage/getall")
    public List<HomePageImage> getAllNoteBooks() {
        return homePageImageService.getAllHomePageImage();
    }

    @GetMapping("/api/HomePageImage/get/{id}")
    public HomePageImage getProductById(@PathVariable int id) {
        return homePageImageService.getHomePageImageById(id);
    }


    @PostMapping("/api/HomePageImage/save")
    public ResponseEntity<String> saveHomePageImage(
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3,
            @RequestPart("imaged") MultipartFile image4
    ) throws IOException {
        homePageImageService.saveHomePageImages(image1, image2, image3, image4);
        return new ResponseEntity<>("Home page images saved successfully", HttpStatus.OK);
    }


    @PutMapping("/api/HomePageImage/update/{id}")
    public ResponseEntity<String> updateHomePageImage(
            @PathVariable int id,
            @RequestPart(value = "imagea", required = false) MultipartFile imagea,
            @RequestPart(value = "imageb", required = false) MultipartFile imageb,
            @RequestPart(value = "imagec", required = false) MultipartFile imagec,
            @RequestPart(value = "imaged", required = false) MultipartFile imaged
    ) throws IOException {
        try {
            homePageImageService.updatehomePageImage(id, imagea, imageb, imagec, imaged);
            return ResponseEntity.ok("Homepage image updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HomePageImage not found with this ID");
        }
    }





    @DeleteMapping("/api/HomePageImage/delete/{id}")
    public ResponseEntity<String> deleteHomePageImage(@PathVariable int id) {
        try {
            homePageImageService.deleteHomePageImage(id);
            return ResponseEntity.ok("HomePageImage deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HomePageImage not found with this ID");
        }
    }


}
