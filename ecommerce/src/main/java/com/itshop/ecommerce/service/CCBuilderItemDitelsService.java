package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.CCBuilder;
import com.itshop.ecommerce.entity.CCBuilderItemDitels;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.repository.CCBuilderItemDitelsRepository;
import com.itshop.ecommerce.repository.CCBuilderRepository;
import com.itshop.ecommerce.repository.ItemRepository;
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



    // Create
    public CCBuilderItemDitels savePcForPart(CCBuilderItemDitels pcForPartAdd, MultipartFile image1File) throws IOException {
        int builderId = pcForPartAdd.getCcBuilder().getId();
        CCBuilder builder = ccBuilderRepository.findById(builderId)
                .orElseThrow(() -> new RuntimeException("PcBuilder with id " + builderId + " not found"));
        pcForPartAdd.setCcBuilder(builder);


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







//
//    public CCBuilderItemDitels updateItem(int id, CCBuilderItemDitels updatedItem) {
//        return repository.findById(id).map(item -> {
//            item.setName(updatedItem.getName());
//            item.setDescription(updatedItem.getDescription());
//            item.setPerformance(updatedItem.getPerformance());
//            item.setAbility(updatedItem.getAbility());
//            item.setRegularprice(updatedItem.getRegularprice());
//            item.setSpecialprice(updatedItem.getSpecialprice());
//            item.setBenefits(updatedItem.getBenefits());
//            item.setMoralqualities(updatedItem.getMoralqualities());
//            item.setOpportunity(updatedItem.getOpportunity());
//            item.setQuantity(updatedItem.getQuantity());
//            item.setImagea(updatedItem.getImagea());
//            item.setCcBuilder(updatedItem.getCcBuilder());
//            item.setItem(updatedItem.getItem());
//            return repository.save(item);
//        }).orElse(null);
//    }
//
//    public void deleteItem(int id) {
//        repository.deleteById(id);
//    }
}
