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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AllLaptopService {



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
 private AllLaptopRepository allLaptopReopsitory;




    public List<AllLaptop> getAllProductDetails() {
        return allLaptopReopsitory.findAll();
    }



    public AllLaptop getAllLaptopById(int id) {
        return allLaptopReopsitory.findById(id).orElse(null);
    }


    @Transactional
    public void saveallLaptop(AllLaptop allLaptop, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File) throws IOException {

        if (allLaptop.getCatagory() != null && allLaptop.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(allLaptop.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            allLaptop.setCatagory(cat);
        } else {
            allLaptop.setCatagory(null);
        }

        if (allLaptop.getProduct() != null && allLaptop.getProduct().getId() != 0) {
            Product prod = productRepository.findById(allLaptop.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            allLaptop.setProduct(prod);
        } else {
            allLaptop.setProduct(null);
        }

        if (allLaptop.getBrand() != null && allLaptop.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(allLaptop.getBrand().getId())
                    .orElse(null);
            allLaptop.setBrand(brand);
        } else {
            allLaptop.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, allLaptop);
            allLaptop.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, allLaptop);
            allLaptop.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, allLaptop);
            allLaptop.setImagec(imageFileName);
        }


        allLaptopReopsitory.save(allLaptop);
    }


    public String saveImage(MultipartFile file, AllLaptop l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/allLaptop");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }




    public List<AllLaptop> filterAllLaptop(
            String generation,
            String processortype,
            Integer warranty,
            String displaysizerange,
            String ram,
            String graphicsmemory,
            String operatingsystem,
            String color,
            String weightrange,
            String fingerprintsensor,
            String lan,
            String graphicschipset,
            String maxramsupport,
            String touchscreen,
            String displayresolutionrange,
            String catagoryName,
            String productName,
            String brandName,
            String productItemName,
            Double regularprice
    ) {
        return allLaptopReopsitory.filterAllLaptops(
                generation, processortype, warranty, displaysizerange,
                ram, graphicsmemory, operatingsystem, color, weightrange,
                fingerprintsensor, lan, graphicschipset, maxramsupport,
                touchscreen, displayresolutionrange, catagoryName,
                productName, brandName, regularprice, productItemName
        );
    }


}
