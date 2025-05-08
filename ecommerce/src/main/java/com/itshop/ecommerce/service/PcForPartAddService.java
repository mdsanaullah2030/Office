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
    public PcForPartAdd savePcForPart(PcForPartAdd pcForPartAdd,MultipartFile image1File) throws IOException {
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


    // Delete
    public void deletePcForPart(int id) {
        pcForPartAddRepository.deleteById(id);
    }


}

