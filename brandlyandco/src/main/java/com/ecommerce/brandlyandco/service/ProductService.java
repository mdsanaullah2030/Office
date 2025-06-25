package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.*;
import com.ecommerce.brandlyandco.repository.*;
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
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Product> getAllProduct(){
        return productRepository.findAll();

    }


    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }




    //Catagory ID By Get All Product Details
    public List<Product> getProductByCatagoryId(int catagoryId) {
        return productRepository.findByCatagoryId(catagoryId);
    }




    @Transactional
    public void saveProduct(Product product, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File, MultipartFile image4File) throws IOException {
        if (image1File != null && !image1File.isEmpty()) {
            product.setImagea(saveImage(image1File));
        }
        if (image2File != null && !image2File.isEmpty()) {
            product.setImageb(saveImage(image2File));
        }
        if (image3File != null && !image3File.isEmpty()) {
            product.setImagec(saveImage(image3File));
        }
        if (image4File != null && !image4File.isEmpty()) {
            product.setImaged(saveImage(image4File));
        }

        productRepository.save(product);
    }


    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/product");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath); // Auto-create folder if not exists
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);

        return filename;
    }





    //Updete
    @Transactional
    public Product updateProduct(
            int id,
            Product incoming,
            MultipartFile image1File,
            MultipartFile image2File,
            MultipartFile image3File
    ) throws IOException {
        // 1) Fetch existing product
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // 2) Update fields from incoming
        existing.setProductname(incoming.getProductname());
        existing.setQuantity(incoming.getQuantity());
        existing.setRegularprice(incoming.getRegularprice());
        existing.setSpecialprice(incoming.getSpecialprice());
        existing.setModel(incoming.getModel());
        existing.setRating(incoming.getRating());
        existing.setDescription(incoming.getDescription());
        existing.setWarranty(incoming.getWarranty());
        existing.setSalesservice(incoming.getSalesservice());
        existing.setPolicy(incoming.getPolicy());
        existing.setOffer(incoming.getOffer());
        existing.setCatagory(incoming.getCatagory());

        // 3) Update images only if new ones are provided
        if (image1File != null && !image1File.isEmpty()) {
            String filename = saveImage(image1File);
            existing.setImagea(filename);
        }
        if (image2File != null && !image2File.isEmpty()) {
            String filename = saveImage(image2File);
            existing.setImageb(filename);
        }
        if (image3File != null && !image3File.isEmpty()) {
            String filename = saveImage(image3File);
            existing.setImagec(filename);
        }



        if (incoming.getCatagory() != null && incoming.getCatagory().getId() != 0) {
            Category cat = categoryRepository.findById(incoming.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCatagory(cat);
        } else {
            existing.setCatagory(null);
        }
        // 4) Save and return
        return productRepository.save(existing);
    }


//Delete

    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        productRepository.deleteById(id);
    }


}
