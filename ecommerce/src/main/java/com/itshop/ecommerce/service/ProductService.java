package com.itshop.ecommerce.service;



import com.itshop.ecommerce.entity.Product;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.ProductRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    private ProductRepository productRepository;



    @Value("src/main/resources/static/images")
    private String uploadDir;


    public Product saveProduct(Product product, MultipartFile image1File)
            throws IOException {

        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, product);
            product.setImagea(imageFileName);
        }



        return  productRepository.save(product);
    }



    public String saveImage(MultipartFile file, Product l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/product");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }















    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }


    public Product updateProduct(int id, Product updatedProduct) {
        return productRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setCatagory(updatedProduct.getCatagory());
            return productRepository.save(existing);
        }).orElse(null);
    }




    public List<Product> findProductsByCatagory(String catagoryName) {
        return productRepository.findProductsByCatagoryName(catagoryName);
    }


    public List<Product> findProductsByCatagoryID(int catagoryId){
        return productRepository.findProductsByCatagoryId(catagoryId);
    }



}