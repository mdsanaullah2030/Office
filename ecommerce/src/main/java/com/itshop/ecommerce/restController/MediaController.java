package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Media;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
public class MediaController {

    @Autowired
    private MediaService mediaService;


    @PostMapping("/api/media/save")
    public ResponseEntity<String> savePcBuilder(
             Media media,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        mediaService.savePcBuilder(media, file);

        return new ResponseEntity<>("media  succesfully with image", HttpStatus.OK);

    }



    @GetMapping("/api/media/get")
    public ResponseEntity<List<Media>> getAll() {
        return ResponseEntity.ok(mediaService.getAllmedia());
    }

    @GetMapping("/api/media/getid/{id}")
    public ResponseEntity<Media> getById(@PathVariable int id) {
        return mediaService.getmediaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/api/media/Delete/{Id}")
    public ResponseEntity<String> removeFromCart(@PathVariable("id") int id) {
        try {
            if (mediaService.getmediaById(id).isPresent()) {
                mediaService.DeleteMedia(id);
                return ResponseEntity.ok("Media deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Media not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting media: " + e.getMessage());
        }


}
}
