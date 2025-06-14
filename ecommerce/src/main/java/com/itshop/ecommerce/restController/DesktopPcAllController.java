package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.DesktopPcAll;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.DesktopPcAllService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
@AllArgsConstructor
public class DesktopPcAllController {

    @Autowired
    private DesktopPcAllService desktopPcAllService;


    @GetMapping("/api/Desktoppcall/getall")
    public List<DesktopPcAll> getAllNoteBooks() {
        return desktopPcAllService.getAllProductDetails();
    }




    @PostMapping("/api/desktoppcall/save")
    public ResponseEntity<String> saveProductDetails(
            @RequestPart("desktoppcall") DesktopPcAll desktopPcAll,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3

    )   throws IOException {
        desktopPcAllService.saveDesktopPcAll(desktopPcAll, image1, image2,image3);
        return new ResponseEntity<>("ProductDetails saved successfully with images", HttpStatus.OK);
    }








    //Filter
    @GetMapping("/api/desktoppcall/filter")
    public ResponseEntity<List<DesktopPcAll>> filterDesktopProducts(
            @RequestParam(required = false) String processorbrand,
            @RequestParam(required = false) String generation,
            @RequestParam(required = false) String processortype,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) String displaysizerange,
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String graphicsmemory,
            @RequestParam(required = false) String operatingsystem,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName
    ) {
        List<DesktopPcAll> results = desktopPcAllService.filterDesktopProducts(
                processorbrand, generation, processortype, warranty,
                displaysizerange, ram, graphicsmemory, operatingsystem, color,
                catagoryName, productName, brandName, productItemName
        );
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



}
