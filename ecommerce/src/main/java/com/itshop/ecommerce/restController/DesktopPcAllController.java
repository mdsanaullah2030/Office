package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.DesktopPcAll;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.DesktopPcAllService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class DesktopPcAllController {

    @Autowired
    private DesktopPcAllService desktopPcAllService;


    @GetMapping("/api/Desktoppcall/getall")
    public List<DesktopPcAll> getAllNoteBooks() {
        return desktopPcAllService.getAllProductDetails();
    }


    @GetMapping("/api/Desktoppcall/{id}")
    public DesktopPcAll getProductBy(@PathVariable int id) {
        return desktopPcAllService.getProductById(id);
    }

  //


    @GetMapping("/api/DesktopPcAll/byCategory/{catagoryId}")
    public ResponseEntity<List<DesktopPcAll>> getProductDetailsByCatagoryId(@PathVariable int catagoryId) {
        List<DesktopPcAll> productList = desktopPcAllService.getProductDetailsByCatagoryId(catagoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Brand By ID Get All Product Details

    @GetMapping("/api/DesktopPcAll/Brand/get/ById/{id}")
    public ResponseEntity<List<DesktopPcAll>> getProductDetailsByBrandId(@PathVariable("id") int brandId) {
        List<DesktopPcAll> productList = desktopPcAllService.getProductDetailsByBrandId(brandId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Product By ID Get All Product Details

    @GetMapping("/api/DesktopPcAll/Product/get/ById/{id}")
    public ResponseEntity<List<DesktopPcAll>> getProductDetailsByBrandIds(@PathVariable("id") int productId) {
        List<DesktopPcAll> productList = desktopPcAllService.getProductDetailsByProductId(productId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


//



    //

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



    @PutMapping("/api/DesktopPcAll/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable int id,
            @RequestPart("desktoppcall") DesktopPcAll updatedItem,
            @RequestParam(value = "imagea", required = false) MultipartFile image1File,
            @RequestParam(value = "imageb", required = false) MultipartFile image2File,
            @RequestParam(value = "imagec", required = false) MultipartFile image3File
    ) throws IOException {
        desktopPcAllService.updateDesktopPcAll(id, updatedItem, image1File, image2File, image3File);
        return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
    }

//


    @DeleteMapping("/api/DesktopPcAll/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
            desktopPcAllService.deleteDesktopPcAll(id);
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            @RequestParam(required = false) String productItemName,
            @RequestParam(required = false)Double regularprice
    ) {
        List<DesktopPcAll> results = desktopPcAllService.filterDesktopProducts(
                processorbrand, generation, processortype, warranty,
                displaysizerange, ram, graphicsmemory, operatingsystem, color,
                catagoryName, productName, brandName, productItemName,regularprice
        );
        return new ResponseEntity<>(results, HttpStatus.OK);
    }



}
