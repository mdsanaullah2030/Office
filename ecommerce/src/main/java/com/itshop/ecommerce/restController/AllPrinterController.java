package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.entity.AllPrinter;
import com.itshop.ecommerce.service.AllPrinterService;
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
public class AllPrinterController {

    @Autowired
    private AllPrinterService allPrinterService;


    @GetMapping("/api/allPrinter/getall")
    public List<AllPrinter> getAll() {
        return allPrinterService.getAll();



    }


    @GetMapping("/api/AllPrinter/{id}")
    public AllPrinter getProductBy(@PathVariable int id) {
        return allPrinterService.getAllPrinterById(id);
    }



//  byCategory ID  //

    @GetMapping("/api/AllPrinter/byCategory/{catagoryId}")
    public ResponseEntity<List<AllPrinter>> getProductDetailsByCatagoryId(@PathVariable int catagoryId) {
        List<AllPrinter> productList = allPrinterService.getAllPrinterByCatagoryId(catagoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Brand By ID Get All Product Details

    @GetMapping("/api/AllPrinter/Brand/get/ById/{id}")
    public ResponseEntity<List<AllPrinter>> getProductDetailsByBrandId(@PathVariable("id") int brandId) {
        List<AllPrinter> productList = allPrinterService.getAllPrinterByBrandId(brandId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Product By ID Get All Product Details

    @GetMapping("/api/AllPrinter/Product/get/ById/{id}")
    public ResponseEntity<List<AllPrinter>> getProductDetailsByBrandIds(@PathVariable("id") int productId) {
        List<AllPrinter> productList = allPrinterService.getAllPrinterByProductId(productId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


//




    @PostMapping("/api/allprinter/save")
    public ResponseEntity<String> saveallLaptop(
            @RequestPart("allPrinter") AllPrinter allPrinter,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3

    )   throws IOException {
        allPrinterService.saveallPrinter(allPrinter, image1, image2,image3);
        return new ResponseEntity<>("allLaptop saved successfully with images", HttpStatus.OK);
    }


    //Filter
    @GetMapping("/api/allprinter/filter")
    public ResponseEntity<List<AllPrinter>> filterAllPrinter(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String printspeed,
            @RequestParam(required = false) String printwidth,
            @RequestParam(required = false) String printresolution,
            @RequestParam(required = false) String interfaces,
            @RequestParam(required = false) String bodycolor,
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName
    ) {
        List<AllPrinter> filteredPrinters = allPrinterService.filterAllPrinters(
                type, printspeed, printwidth, printresolution, interfaces, bodycolor,
                regularprice, warranty, catagoryName, productName, brandName, productItemName
        );
        return ResponseEntity.ok(filteredPrinters);
    }



    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        allPrinterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
