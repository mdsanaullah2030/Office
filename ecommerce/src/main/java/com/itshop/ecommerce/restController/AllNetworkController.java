package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.service.AllNetworkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping
@CrossOrigin
public class AllNetworkController {

    @Autowired
    private  AllNetworkService allNetworkService;



    @GetMapping("/api/AllNetwork/getall")
    public List<AllNetwork> getAllNoteBooks() {
        return allNetworkService.getAllNetworks();
    }



    @GetMapping("/api/AllNetwork/{id}")
    public AllNetwork getProductBy(@PathVariable int id) {
        return allNetworkService.getNetworkById(id);
    }





    @PostMapping("/api/allnetwork/save")
    public ResponseEntity<String> saveallLaptop(
            @RequestPart("allnetwork") AllNetwork allNetwork,
            @RequestPart("imagea") MultipartFile image1,
            @RequestPart("imageb") MultipartFile image2,
            @RequestPart("imagec") MultipartFile image3

    )   throws IOException {
        allNetworkService.saveallLaptop(allNetwork, image1, image2,image3);
        return new ResponseEntity<>("allNetwork saved successfully with images", HttpStatus.OK);
    }





    //Filter
    @GetMapping("/api/AllNetwork/filter")
    public ResponseEntity<List<AllNetwork>> filterAllNetwork(
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String portside,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName
    ) {
        List<AllNetwork> filteredNetworks = allNetworkService.FilterAllNetwork(
                color, portside, regularprice, warranty, catagoryName, productName,
                brandName, productItemName);
        return ResponseEntity.ok(filteredNetworks);
    }


}
