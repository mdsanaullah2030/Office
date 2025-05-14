package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.HomePageImage;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.HomePageImageRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class HomePageImageService {
    @Autowired
    private HomePageImageRepository homePageImageRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;



    public List<HomePageImage> getAllHomePageImage(){
        return homePageImageRepository.findAll();

    }


    public HomePageImage getHomePageImageById(int id) {
        return homePageImageRepository.findById(id).orElse(null);
    }


    @Transactional
    public void saveHomePageImages(MultipartFile image1File, MultipartFile image2File, MultipartFile image3File, MultipartFile image4File) throws IOException {
        HomePageImage homePageImage = new HomePageImage();

        if (image1File != null && !image1File.isEmpty()) {
            homePageImage.setImagea(saveImage(image1File));
        }
        if (image2File != null && !image2File.isEmpty()) {
            homePageImage.setImageb(saveImage(image2File));
        }
        if (image3File != null && !image3File.isEmpty()) {
            homePageImage.setImagec(saveImage(image3File));
        }
        if (image4File != null && !image4File.isEmpty()) {
            homePageImage.setImaged(saveImage(image4File));
        }

        homePageImageRepository.save(homePageImage);
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/homeimage");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);

        return filename;
    }




    @Transactional
    public void updatehomePageImage(int id, MultipartFile image1File, MultipartFile image2File,
                                    MultipartFile image3File, MultipartFile image4File) throws IOException {

        HomePageImage existing = homePageImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with this ID"));

        if (image1File != null && !image1File.isEmpty()) {
            String filename = saveImage(image1File); // fixed method call
            existing.setImagea(filename);
        }
        if (image2File != null && !image2File.isEmpty()) {
            String filename = saveImage(image2File); // fixed method call
            existing.setImageb(filename);
        }
        if (image3File != null && !image3File.isEmpty()) {
            String filename = saveImage(image3File); // fixed method call
            existing.setImagec(filename);
        }
        if (image4File != null && !image4File.isEmpty()) {
            String filename = saveImage(image4File); // fixed method call
            existing.setImaged(filename);
        }

        homePageImageRepository.save(existing);
    }




    public void deleteHomePageImage(int id) {
        HomePageImage image = homePageImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with this ID"));

        homePageImageRepository.delete(image);
    }


}
