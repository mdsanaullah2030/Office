package com.itshop.ecommerce.service;



import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.repository.PcForPartAddRepository;
import com.itshop.ecommerce.repository.PcBuilderRepository;
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
public class PcForPartAddService {

    @Autowired
    private PcForPartAddRepository pcForPartAddRepository;

    @Autowired
    private PcBuilderRepository pcBuilderRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    // Create
    public PcForPartAdd savePcForPart(PcForPartAdd pcForPartAdd, MultipartFile image1File) throws IOException {
        int builderId = pcForPartAdd.getPcbuilder().getId();
        PcBuilder builder = pcBuilderRepository.findById(builderId)
                .orElseThrow(() -> new RuntimeException("PcBuilder with id " + builderId + " not found"));
        pcForPartAdd.setPcbuilder(builder);


        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, pcForPartAdd);
            pcForPartAdd.setImagea(imageFileName);
        }


        return pcForPartAddRepository.save(pcForPartAdd);
    }


    public String saveImage(MultipartFile file, PcForPartAdd l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/pcforpartadd");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }


    // Read all
    public List<PcForPartAdd> getAllCpu() {
        return pcForPartAddRepository.findAll();
    }

    // Read by ID
    public Optional<PcForPartAdd> getCpuById(int id) {
        return pcForPartAddRepository.findById(id);
    }

    public PcForPartAdd updatePcForPartAdd(int id, PcForPartAdd pcForPartadd) {
        PcForPartAdd existingCpu = pcForPartAddRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CPU not found"));

        existingCpu.setName(pcForPartadd.getName());
        existingCpu.setDescription(pcForPartadd.getDescription());
        existingCpu.setPerformance(pcForPartadd.getPerformance());
        existingCpu.setAbility(pcForPartadd.getAbility());
        existingCpu.setSpecialprice(pcForPartadd.getSpecialprice());
        existingCpu.setSpecialprice(pcForPartadd.getSpecialprice());

        return pcForPartAddRepository.save(existingCpu);
    }

//Updete

    public PcForPartAdd updatePcPart(int id, PcForPartAdd incoming, MultipartFile imageFile) throws IOException {
        PcForPartAdd existing = pcForPartAddRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PC part not found with ID: " + id));

        existing.setName(incoming.getName());
        existing.setDescription(incoming.getDescription());
        existing.setPerformance(incoming.getPerformance());
        existing.setAbility(incoming.getAbility());
        existing.setRegularprice(incoming.getRegularprice());
        existing.setSpecialprice(incoming.getSpecialprice());
        existing.setWarranty(incoming.getWarranty());
        existing.setQuantity(incoming.getQuantity());

        // Handle image
        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = saveImage(imageFile, existing);
            existing.setImagea(filename);
        }

        // Update relation if needed
        if (incoming.getPcbuilder() != null && incoming.getPcbuilder().getId() != 0) {
            PcBuilder builder = pcBuilderRepository.findById(incoming.getPcbuilder().getId())
                    .orElseThrow(() -> new RuntimeException("PC Builder not found"));
            existing.setPcbuilder(builder);
        } else {
            existing.setPcbuilder(null);
        }

        return pcForPartAddRepository.save(existing);
    }








    // Delete
    public void deleteProductDetils(int id) {
        if (!pcForPartAddRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }


        // Now safely delete the item
        pcForPartAddRepository.deleteById(id);
    }


    public List<PcForPartAdd> getPartsByPcBuilderId(int id) {
        PcBuilder pcBuilder = pcBuilderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PcBuilder not found with ID: " + id));
        return pcForPartAddRepository.findByPcBuilderId(pcBuilder.getId());


    }



    public List<PcForPartAdd> filterPcParts(Double regularprice, Integer warranty, Integer pcbuilderId) {
        return pcForPartAddRepository.filterByPriceWarrantyAndPcbuilder(regularprice, warranty, pcbuilderId);
    }

}
