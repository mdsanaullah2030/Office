package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.HomeScreen;
import com.ecommerce.brandlyandco.service.HomeScreenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class HomeScreenController {
@Autowired
private HomeScreenService homeScreenService;



    @PostMapping("/api/homeScreen/save")
    public ResponseEntity<String> savePcBuilder(
            HomeScreen media,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        homeScreenService.saveHomeScreen(media, file);

        return new ResponseEntity<>("media  succesfully with image", HttpStatus.OK);

    }

//

    @GetMapping("/api/homeScreen/get")
    public ResponseEntity<List <HomeScreen>> getAll() {
        return ResponseEntity.ok(homeScreenService.getAllmedia());
    }

    @GetMapping("/api/homeScreen/get/{id}")
    public ResponseEntity<HomeScreen> getMediaId(@PathVariable int id) {
        HomeScreen media = homeScreenService.getMediaById(id);
        return ResponseEntity.ok(media);
    }

    @DeleteMapping("/api/homeScreen/delete/{id}") // use lowercase `id`
    public ResponseEntity<String> removeFromCart(@PathVariable("id") int id) {
        try {
            HomeScreen media = homeScreenService.getMediaById(id); // throws if not found
            homeScreenService.deleteMedia(id); // delete by id
            return ResponseEntity.ok("Media deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting media: " + e.getMessage());
        }
    }
}
