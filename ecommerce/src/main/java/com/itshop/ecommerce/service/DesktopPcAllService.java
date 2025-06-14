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
public class DesktopPcAllService {



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
    private DesktopPcAllRepository desktopPcAllRepository;



    public List<DesktopPcAll> getAllProductDetails(){
        return desktopPcAllRepository.findAll();

    }


    public DesktopPcAll getProductById(int id) {
        return desktopPcAllRepository.findById(id).orElse(null);
    }




    @Transactional
    public void saveDesktopPcAll(DesktopPcAll desktopPcAll, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File) throws IOException {

        if (desktopPcAll.getCatagory() != null && desktopPcAll.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(desktopPcAll.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            desktopPcAll.setCatagory(cat);
        } else {
            desktopPcAll.setCatagory(null);
        }

        if (desktopPcAll.getProduct() != null && desktopPcAll.getProduct().getId() != 0) {
            Product prod = productRepository.findById(desktopPcAll.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            desktopPcAll.setProduct(prod);
        } else {
            desktopPcAll.setProduct(null);
        }

        if (desktopPcAll.getBrand() != null && desktopPcAll.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(desktopPcAll.getBrand().getId())
                    .orElse(null);
            desktopPcAll.setBrand(brand);
        } else {
            desktopPcAll.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, desktopPcAll);
            desktopPcAll.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, desktopPcAll);
            desktopPcAll.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, desktopPcAll);
            desktopPcAll.setImagec(imageFileName);
        }



        desktopPcAllRepository.save(desktopPcAll);
    }




    public String saveImage(MultipartFile file, DesktopPcAll l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/desktop");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }




    public List<DesktopPcAll> filterDesktopProducts(
            String processorbrand,
            String generation,
            String processortype,
            Integer warranty,
            String displaysizerange,
            String ram,
            String graphicsmemory,
            String operatingsystem,
            String color,
            String catagoryName,
            String productName,
            String brandName,
            String productItemName
    ) {
        return desktopPcAllRepository.filterDesktopProducts(
                processorbrand, generation, processortype, warranty,
                displaysizerange, ram, graphicsmemory, operatingsystem, color,
                catagoryName, productName, brandName, productItemName
        );
    }


}
