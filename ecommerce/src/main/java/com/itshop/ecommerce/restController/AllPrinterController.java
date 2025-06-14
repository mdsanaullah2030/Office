package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllLaptop;
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








    // Read All
    @GetMapping("/getall")
    public ResponseEntity<List<AllPrinter>> getAll() {
        return ResponseEntity.ok(allPrinterService.getAll());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<AllPrinter> getById(@PathVariable int id) {
        return allPrinterService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
//    @PutMapping("/update/{id}")
//    public ResponseEntity<AllPrinter> update(@PathVariable int id, @RequestBody AllPrinter printer) {
//        return ResponseEntity.ok(allPrinterService.update(id, printer));
//    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        allPrinterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
