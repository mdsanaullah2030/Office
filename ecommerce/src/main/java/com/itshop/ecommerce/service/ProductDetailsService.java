package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.CatagoryRepository;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import org.apache.catalina.LifecycleState;
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
public class ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;
    @Autowired
    private CatagoryRepository catagoryRepository;

    public List<ProductDetails> getAllProductDetails(){
        return productDetailsRepository.findAll();


    }

    @Transactional
    public void saveProductDetails(ProductDetails productDetails, MultipartFile image1File, MultipartFile image2File,MultipartFile image3File) throws IOException {


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, productDetails);
            productDetails.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, productDetails); //  Correct: image2File
            productDetails.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, productDetails);
            productDetails.setImagec(imageFileName);
        }



        productDetailsRepository.save(productDetails); //  You forgot to save it in the database!
    }



    public String saveImage(MultipartFile file, ProductDetails l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/location");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = l.getName() + "_" + UUID.randomUUID().toString();
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }

}