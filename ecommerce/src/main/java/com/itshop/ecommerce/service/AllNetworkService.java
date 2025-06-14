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
public class AllNetworkService {

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
    private AllNetworkRepository allNetworkRepository;

    public AllNetwork saveAllNetwork(AllNetwork network) {
        return allNetworkRepository.save(network);
    }

    public List<AllNetwork> getAllNetworks() {
        return allNetworkRepository.findAll();
    }

    public Optional<AllNetwork> getNetworkById(int id) {
        return allNetworkRepository.findById(id);
    }

    @Transactional
    public void saveallLaptop(AllNetwork allNetwork, MultipartFile image1File, MultipartFile image2File, MultipartFile image3File) throws IOException {

        if (allNetwork.getCatagory() != null && allNetwork.getCatagory().getId() != 0) {
            Catagory cat = catagoryRepository.findById(allNetwork.getCatagory().getId())
                    .orElseThrow(() -> new RuntimeException("Catagory not found"));
            allNetwork.setCatagory(cat);
        } else {
            allNetwork.setCatagory(null);
        }

        if (allNetwork.getProduct() != null && allNetwork.getProduct().getId() != 0) {
            Product prod = productRepository.findById(allNetwork.getProduct().getId())
                    .orElse(null);  // orThrow if needed
            allNetwork.setProduct(prod);
        } else {
            allNetwork.setProduct(null);
        }

        if (allNetwork.getBrand() != null && allNetwork.getBrand().getId() != 0) {
            Brand brand = brandRepository.findById(allNetwork.getBrand().getId())
                    .orElse(null);
            allNetwork.setBrand(brand);
        } else {
            allNetwork.setBrand(null);
        }


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, allNetwork);
            allNetwork.setImagea(imageFileName);
        }

        if (image2File != null && !image2File.isEmpty()) {
            String imageFileName = saveImage(image2File, allNetwork);
            allNetwork.setImageb(imageFileName);
        }


        if (image3File != null && !image3File.isEmpty()) {
            String imageFileName = saveImage(image3File, allNetwork);
            allNetwork.setImagec(imageFileName);
        }



        allNetworkRepository.save(allNetwork);
    }




    public String saveImage(MultipartFile file, AllNetwork l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/allnetwork");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }


    public void deleteNetwork(int id) {
        allNetworkRepository.deleteById(id);
    }
}
