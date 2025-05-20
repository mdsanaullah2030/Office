package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.CCBuilderItemDitelsRepository;
import com.itshop.ecommerce.repository.CCBuilderRepository;
import com.itshop.ecommerce.repository.ItemRepository;
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
public class CCBuilderItemDitelsService {

    @Autowired
    private CCBuilderItemDitelsRepository ccBuilderItemDitelsRepository;

    @Autowired
    private CCBuilderRepository ccBuilderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<CCBuilderItemDitels> getAllCCBuilderItemDitels() {
        return ccBuilderItemDitelsRepository.findAll();
    }

    public Optional<CCBuilderItemDitels> getCCBuilderItemDitelsById(int id) {
        return ccBuilderItemDitelsRepository.findById(id);
    }



    public List<CCBuilderItemDitels> getccBuilderItemDitelsByItemId(int itemId) {
        return ccBuilderItemDitelsRepository.findByItemId(itemId);
    }



    public List<CCBuilderItemDitels> getccBuilderItemDitelsCCBuilderId(int ccBuilderId) {
        return ccBuilderItemDitelsRepository.findByCcBuilder_Id(ccBuilderId);
    }








    // Create
    public CCBuilderItemDitels savePcForPart(CCBuilderItemDitels pcForPartAdd, MultipartFile image1File) throws IOException {

        int builderId = pcForPartAdd.getCcBuilder().getId();
        CCBuilder builder = ccBuilderRepository.findById(builderId)
                .orElseThrow(() -> new RuntimeException("PcBuilder with id " + builderId + " not found"));
        pcForPartAdd.setCcBuilder(builder);

        // Only set item if it exists
        if (pcForPartAdd.getItem() != null && pcForPartAdd.getItem().getId() != 0) {
            int itemId = pcForPartAdd.getItem().getId();
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item with id " + itemId + " not found"));
            pcForPartAdd.setItem(item);
        } else {
            pcForPartAdd.setItem(null);  // explicitly set to null to be safe
        }

        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, pcForPartAdd);
            pcForPartAdd.setImagea(imageFileName);
        }

        return ccBuilderItemDitelsRepository.save(pcForPartAdd);
    }



    public String saveImage(MultipartFile file, CCBuilderItemDitels l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/ccbuilderitem");

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
    public CCBuilderItemDitels updateItem(int id, CCBuilderItemDitels updatedItem, MultipartFile image1File) throws IOException {
        CCBuilderItemDitels existing = ccBuilderItemDitelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));

        existing.setName(updatedItem.getName());
        existing.setDescription(updatedItem.getDescription());
        existing.setPerformance(updatedItem.getPerformance());
        existing.setAbility(updatedItem.getAbility());
        existing.setRegularprice(updatedItem.getRegularprice());
        existing.setSpecialprice(updatedItem.getSpecialprice());
        existing.setQuantity(updatedItem.getQuantity());
        existing.setBenefits(updatedItem.getBenefits());
        existing.setMoralqualities(updatedItem.getMoralqualities());
        existing.setOpportunity(updatedItem.getOpportunity());

        // Update image if a new file is provided
        if (image1File != null && !image1File.isEmpty()) {
            String filename = saveImage(image1File, existing);
            existing.setImagea(filename);
        }

        // Optional item relationship
        if (updatedItem.getItem() != null && updatedItem.getItem().getId() != 0) {
            Item item = itemRepository.findById(updatedItem.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found with ID: " + updatedItem.getItem().getId()));
            existing.setItem(item);
        } else {
            existing.setItem(null);
        }

        // Optional CCBuilder relationship
        if (updatedItem.getCcBuilder() != null && updatedItem.getCcBuilder().getId() != 0) {
            CCBuilder builder = ccBuilderRepository.findById(updatedItem.getCcBuilder().getId())
                    .orElseThrow(() -> new RuntimeException("Builder not found with ID: " + updatedItem.getCcBuilder().getId()));existing.setCcBuilder(builder);
        }

        return ccBuilderItemDitelsRepository.save(existing);
    }






    public void deleteItem(int id) {
        if (!ccBuilderItemDitelsRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }
        ccBuilderItemDitelsRepository.deleteById(id);
    }




///Filter


    public List<CCBuilderItemDitels> filterCCBuilderItems(Double regularprice, Integer warranty, Integer ccBuilderId, Integer itemId) {
        return ccBuilderItemDitelsRepository.filterByRegularpriceWarrantyCCBuilderAndItem(
                regularprice, warranty, ccBuilderId, itemId
        );
    }




}
