package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.HomeScreen;
import com.ecommerce.brandlyandco.repository.HomeScreenRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class HomeScreenService {
    @Autowired
    private HomeScreenRepository homeScreenRepository;



    @Value("src/main/resources/static/images")
    private String uploadDir;


    public List<HomeScreen> getAllmedia() {
        return homeScreenRepository.findAll();
    }



//Delete

    public HomeScreen getMediaById(int id) {
        return homeScreenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + id));
    }

    public void deleteMedia(int id) {
        homeScreenRepository.deleteById(id);
    }




    public HomeScreen saveHomeScreen(HomeScreen media, MultipartFile image1File)
            throws IOException {

        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, media);
            media.setImagea(imageFileName);
        }



        return  homeScreenRepository.save(media);
    }



    public String saveImage(MultipartFile file, HomeScreen l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/homescreen");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }

}
