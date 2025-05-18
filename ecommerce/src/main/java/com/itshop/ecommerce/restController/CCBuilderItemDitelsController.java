package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.CCBuilderItemDitels;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.service.CCBuilderItemDitelsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
@AllArgsConstructor
public class CCBuilderItemDitelsController {

    @Autowired
    private CCBuilderItemDitelsService service;

    @GetMapping("/api/CCBuilder/Item/Ditels/get")
    public List<CCBuilderItemDitels> getAllItems() {
        return service.getAllCCBuilderItemDitels();
    }

    @GetMapping("/api/CCBuilder/Item/Ditels/get/{id}")
    public CCBuilderItemDitels getItemById(@PathVariable int id) {
        return service.getCCBuilderItemDitelsById(id).orElse(null);
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

//
//    @PutMapping("/{id}")
//    public CCBuilderItemDitels updateItem(@PathVariable int id, @RequestBody CCBuilderItemDitels item) {
//        return service.updateItem(id, item);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteItem(@PathVariable int id) {
//        service.deleteItem(id);
//    }
}
