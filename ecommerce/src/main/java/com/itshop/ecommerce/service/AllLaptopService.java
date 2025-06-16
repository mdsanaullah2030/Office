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


    //Updete
    @Transactional
    public AllLaptop updateAllLaptop(
            int id,
            AllLaptop incoming,
            MultipartFile image1File,
            MultipartFile image2File,
            MultipartFile image3File
    ) throws IOException {

        // 1) Fetch existing product
        AllLaptop existing = allLaptopReopsitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // 2) Copy scalar fields
        existing.setName(incoming.getName());
        existing.setQuantity(incoming.getQuantity());
        existing.setRegularprice(incoming.getRegularprice());
        existing.setSpecialprice(incoming.getSpecialprice());
        existing.setTitle(incoming.getTitle());
        existing.setDetails(incoming.getDetails());
        existing.setSpecification(incoming.getSpecification());
        existing.setWarranty(incoming.getWarranty());
        existing.setGeneration(incoming.getGeneration());
        existing.setProcessortype(incoming.getProcessortype());
        existing.setDisplaysizerange(incoming.getDisplaysizerange());
        existing.setRam(incoming.getRam());
        existing.setGraphicsmemory(incoming.getGraphicsmemory());
        existing.setOperatingsystem(incoming.getOperatingsystem());
        existing.setDisplayresolutionrange(incoming.getDisplayresolutionrange());
        existing.setTouchscreen(incoming.getTouchscreen());
        existing.setMaxramsupport(incoming.getMaxramsupport());
        existing.setGraphicschipset(incoming.getGraphicschipset());
        existing.setLan(incoming.getLan());
        existing.setFingerprintsensor(incoming.getFingerprintsensor());
        existing.setWeightrange(incoming.getWeightrange());
        existing.setColor(incoming.getColor());

        // 3) Handle images
        if (image1File != null && !image1File.isEmpty()) {
            String filename = saveImage(image1File, existing);
            existing.setImagea(filename);
        }
        if (image2File != null && !image2File.isEmpty()) {
            String filename = saveImage(image2File, existing);
            existing.setImageb(filename);
        }
        if (image3File != null && !image3File.isEmpty()) {
            String filename = saveImage(image3File, existing);
            existing.setImagec(filename);
        }

        // 4) Handle relationships
        if (incoming.getCatagory() != null && incoming.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(incoming.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCatagory(cat);
        } else {
            existing.setCatagory(null);
        }

        if (incoming.getProduct() != null && incoming.getProduct().getId() != 0) {
            Product prod = productRepository.findById(incoming.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existing.setProduct(prod);
        } else {
            existing.setProduct(null);
        }

        if (incoming.getProductItem() != null && incoming.getProductItem().getId() != 0) {
            ProductItem prodItem = productItemRepository.findById(incoming.getProductItem().getId())
                    .orElseThrow(() -> new RuntimeException("Product Item not found"));
            existing.setProductItem(prodItem);
        } else {
            existing.setProductItem(null);
        }

        if (incoming.getBrand() != null && incoming.getBrand().getId() != 0) {
            Brand br = brandRepository.findById(incoming.getBrand().getId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            existing.setBrand(br);
        } else {
            existing.setBrand(null);
        }

        // 5) Save and return
        return allLaptopReopsitory.save(existing);
    }




    public void deleteAlLLaptop(int id) {
        if (!allLaptopReopsitory.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        allLaptopReopsitory.deleteById(id);
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
