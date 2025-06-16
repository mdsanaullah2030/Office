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



    //Updete
    @Transactional
    public DesktopPcAll updateDesktopPcAll(
            int id,
            DesktopPcAll incoming,
            MultipartFile image1File,
            MultipartFile image2File,
            MultipartFile image3File
    ) throws IOException {

        // 1) Fetch existing product
        DesktopPcAll existing = desktopPcAllRepository.findById(id)
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

        existing.setProcessorbrand(incoming.getProcessorbrand());
        existing.setGeneration(incoming.getGeneration());
        existing.setProcessortype(incoming.getProcessortype());
        existing.setDisplaysizerange(incoming.getDisplaysizerange());
        existing.setRam(incoming.getRam());
        existing.setGraphicsmemory(incoming.getGraphicsmemory());
        existing.setOperatingsystem(incoming.getOperatingsystem());
        existing.setColor(incoming.getColor());

        // 3) Update image fields
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

        // 4) Copy relationships

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

        // 5) Save and return updated entity
        return desktopPcAllRepository.save(existing);
    }



    public void deleteDesktopPcAll(int id) {
        if (!desktopPcAllRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        desktopPcAllRepository.deleteById(id);
    }





//Filter

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
            String productItemName,
            Double regularprice
    ) {
        return desktopPcAllRepository.filterDesktopProducts(
                processorbrand, generation, processortype, warranty,
                displaysizerange, ram, graphicsmemory, operatingsystem, color,
                catagoryName, productName, brandName, productItemName,regularprice
        );
    }


}
