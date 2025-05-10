package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.Product;
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
public class PcBuilderService {
    @Autowired
    private PcBuilderRepository  pcBuilderRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;




    public PcBuilder savePcBuilder(PcBuilder pcBuilder, MultipartFile image1File)
            throws IOException {

        if (image1File != null && !image1File.isEmpty()) {
            String imageFileName = saveImage(image1File, pcBuilder);
            pcBuilder.setImagea(imageFileName);
        }



        return  pcBuilderRepository.save(pcBuilder);
    }



    public String saveImage(MultipartFile file, PcBuilder l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/pcBuilder");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);


        return filename;
    }






    public List<PcBuilder> getAllPcBuilder() {
        return pcBuilderRepository.findAll();
    }


  public Optional<PcBuilder>getPcBuilderById(int id){
    return pcBuilderRepository.findById(id);
  }





}
