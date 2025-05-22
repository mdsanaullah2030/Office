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
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private  SubCategoryRepository subCategoryRepository;
     @Autowired
    private  ItemRepository itemRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;



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





}
