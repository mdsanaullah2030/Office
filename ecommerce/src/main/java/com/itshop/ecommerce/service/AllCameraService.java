package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class AllCameraService {

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Autowired
    private CatagoryRepository catagoryRepository;


    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private AllCameraRepository allCameraRepository;



    public List<AllCamera> getAllCamera() {
        return allCameraRepository.findAll();
    }



    public AllCamera getAllCameraById(int id) {
        return allCameraRepository.findById(id).orElse(null);
    }




    @Transactional
    public void saveAllCamera(AllCamera allCamera, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File) throws IOException {

        if (allCamera.getCatagory() != null && allCamera.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(allCamera.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            allCamera.setCatagory(cat);
        } else {
            allCamera.setCatagory(null);
        }

        if (allCamera.getProduct() != null && allCamera.getProduct().getId() != 0) {
            Product prod = productRepository.findById(allCamera.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            allCamera.setProduct(prod);
        } else {
            allCamera.setProduct(null);
        }

        if (allCamera.getBrand() != null && allCamera.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(allCamera.getBrand().getId())
                    .orElse(null);
            allCamera.setBrand(brand);
        } else {
            allCamera.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, allCamera);
            allCamera.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, allCamera);
            allCamera.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, allCamera);
            allCamera.setImagec(imageFileName);
        }



        allCameraRepository.save(allCamera);
    }




    public String saveImage(MultipartFile file, AllCamera l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/allCamera");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }


    public List<AllCamera> filterAllCamera(
            String name,
            String totalpixel,
            String displaysize,
            String digitalzoom,
            String opticalzoom,
            Double regularprice,
            Double specialprice,
            Integer warranty,
            String catagoryName,
            String brandName,
            String productItemName
    ) {
        return allCameraRepository.filterAllCamera(
                name, totalpixel, displaysize, digitalzoom, opticalzoom,
                regularprice, specialprice, warranty,
                catagoryName, brandName, productItemName
        );
    }




}
