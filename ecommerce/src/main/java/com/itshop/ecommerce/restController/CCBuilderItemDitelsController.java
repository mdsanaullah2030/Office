package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.CCBuilderItemDitels;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.service.CCBuilderItemDitelsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
@AllArgsConstructor
public class CCBuilderItemDitelsController {

    @Autowired
    private CCBuilderItemDitelsService service;
    @GetMapping("/api/CCBuilder/Item/Ditels/get")
    public ResponseEntity<?> getAllItems() {
        try {
            List<CCBuilderItemDitels> items = service.getAllCCBuilderItemDitels();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/CCBuilder/Item/Ditels/get/{id}")
    public ResponseEntity<?> getItemById(@PathVariable int id) {
        try {
            Optional<CCBuilderItemDitels> item = service.getCCBuilderItemDitelsById(id);
            if (item.isPresent()) {
                return new ResponseEntity<>(item.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/api/CCBuilder/Item/Ditels/save")

    public ResponseEntity<String> savePcForPart(
            @RequestPart(value = "ccbuilder") CCBuilderItemDitels pcForPartAdd,
            @RequestParam(value = "image", required = true) MultipartFile file
    )
    throws IOException {
        service.savePcForPart(pcForPartAdd, file);

        return new ResponseEntity<>("CCBuilder Item Ditels added succesfully with image", HttpStatus.OK);

    }

    @PutMapping("/api/CCBuilder/Item/Ditels/update/{id}")
    public ResponseEntity<String> updateItem(
            @PathVariable int id,
            @RequestPart("ccbuilder") CCBuilderItemDitels updatedItem,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {
        service.updateItem(id, updatedItem, imageFile);
        return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
    }


    @DeleteMapping("/api/CCBuilder/Item/Ditels/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        try {
        service.deleteItem(id);
        return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
    } catch (EntityNotFoundException e) {
        return new ResponseEntity<>("Item not found with ID: " + id, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
        return new ResponseEntity<>("Failed to delete item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}





    ///itemId By ID Get All CCBuilder Product Details

    @GetMapping("/api/CCBuilder/Ditels/itemId/Idby/get/{id}")
    public ResponseEntity<?> getProductDetailsByItemId(@PathVariable("id") int itemId) {
        try {
            List<CCBuilderItemDitels> productList = service.getccBuilderItemDitelsByItemId(itemId);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch item details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    ///ccBuilder By ID Get CCBuilder All Product Details
    @GetMapping("/api/CCBuilder/Ditels/ccBuilder/get/ById/{id}")
    public ResponseEntity<?> getProductDetailsByBuilderId(@PathVariable("id") int ccBuilderId) {
        try {
            List<CCBuilderItemDitels> productList = service.getccBuilderItemDitelsCCBuilderId(ccBuilderId);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch ccBuilder details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping("/api/ccbuilder/items/filter")
    public ResponseEntity<List<CCBuilderItemDitels>> filterCCBuilderItems(
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) Integer ccBuilderId,
            @RequestParam(required = false) Integer itemId
    ) {
        List<CCBuilderItemDitels> results = service.filterCCBuilderItems(
                regularprice, warranty, ccBuilderId, itemId
        );
        return new ResponseEntity<>(results, HttpStatus.OK);
    }






}
