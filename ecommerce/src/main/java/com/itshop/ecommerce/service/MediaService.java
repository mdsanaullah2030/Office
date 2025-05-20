package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.AboutUs;
import com.itshop.ecommerce.entity.Media;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.repository.MediaRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;
    @Value("src/main/resources/static/images")
    private String uploadDir;


    public List<Media> getAllmedia() {
        return mediaRepository.findAll();
    }



//Delete

    public Media getMediaById(int id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + id));
    }

    public void deleteMedia(int id) {
        mediaRepository.deleteById(id);
    }




    public Media savePcBuilder(Media media, MultipartFile image1File)
            throws IOException {

        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, media);
            media.setImagea(imageFileName);
        }



        return  mediaRepository.save(media);
    }



    public String saveImage(MultipartFile file, Media l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/media");

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
