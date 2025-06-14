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
import java.util.Optional;
import java.util.UUID;

@Service
public class AllPrinterService {
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
    private AllPrinterRepository  allPrinterRepository;



    public List<AllPrinter> getAll() {
        return allPrinterRepository.findAll();
    }

    public Optional<AllPrinter> getById(int id) {
        return allPrinterRepository.findById(id);
    }

    @Transactional
    public void saveallPrinter(AllPrinter allPrinter, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File) throws IOException {

        if (allPrinter.getCatagory() != null && allPrinter.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(allPrinter.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            allPrinter.setCatagory(cat);
        } else {
            allPrinter.setCatagory(null);
        }

        if (allPrinter.getProduct() != null && allPrinter.getProduct().getId() != 0) {
            Product prod = productRepository.findById(allPrinter.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            allPrinter.setProduct(prod);
        } else {
            allPrinter.setProduct(null);
        }

        if (allPrinter.getBrand() != null && allPrinter.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(allPrinter.getBrand().getId())
                    .orElse(null);
            allPrinter.setBrand(brand);
        } else {
            allPrinter.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, allPrinter);
            allPrinter.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, allPrinter);
            allPrinter.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, allPrinter);
            allPrinter.setImagec(imageFileName);
        }



        allPrinterRepository.save(allPrinter);
    }




    public String saveImage(MultipartFile file, AllPrinter l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/allPrinter");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }




    public List<AllPrinter> filterAllPrinters(
            String type,
            String printspeed,
            String printwidth,
            String printresolution,
            String interfaces,
            String bodycolor,
            Double regularprice,
            Integer warranty,
            String catagoryName,
            String productName,
            String brandName,
            String productItemName
    ) {
        return allPrinterRepository.filterAllPrinters(
                type, printspeed, printwidth, printresolution, interfaces,
                bodycolor, regularprice, warranty, catagoryName,
                productName, brandName, productItemName
        );
    }







    public void delete(int id) {
        allPrinterRepository.deleteById(id);
    }
}
