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


    //

    public List<AllCamera> getAllCamerapByCatagoryId(int catagoryId) {
        return allCameraRepository.findByCatagoryId(catagoryId);
    }


    public List<AllCamera> getAllCameraByBrandId(int brandId) {
        return allCameraRepository.findByBrandId(brandId);
    }


    public List<AllCamera> getAllCameraByProductId(int productId) {
        return allCameraRepository.findByProductId(productId);
    }
//



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



    @Transactional
    public AllCamera updateAllCamera(
            int id,
            AllCamera incoming,
            MultipartFile image1File,
            MultipartFile image2File,
            MultipartFile image3File
    ) throws IOException {

        // 1) Fetch existing product
        AllCamera existing = allCameraRepository.findById(id)
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
        existing.setTotalpixel(incoming.getTotalpixel());
        existing.setDisplaysize(incoming.getDisplaysize());
        existing.setDigitalzoom(incoming.getDigitalzoom());
        existing.setOpticalzoom(incoming.getOpticalzoom());

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

        // 5) Save and return updated entity
        return allCameraRepository.save(existing);
    }




    public void deleteAllCamera(int id) {
        if (!allCameraRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        allCameraRepository.deleteById(id);
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
