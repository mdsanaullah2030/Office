package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.entity.AllPrinter;
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




//  byCategory ID  //

    @GetMapping("/api/AllNetwork/byCategory/{catagoryId}")
    public ResponseEntity<List<AllNetwork>> getProductDetailsByCatagoryId(@PathVariable int catagoryId) {
        List<AllNetwork> productList = allNetworkService.getAllNetworkByCatagoryId(catagoryId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Brand By ID Get All Product Details

    @GetMapping("/api/AllNetwork/Brand/get/ById/{id}")
    public ResponseEntity<List<AllNetwork>> getProductDetailsByBrandId(@PathVariable("id") int brandId) {
        List<AllNetwork> productList = allNetworkService.getAllNetworkByBrandId(brandId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

///Product By ID Get All Product Details

    @GetMapping("/api/AllNetwork/Product/get/ById/{id}")
    public ResponseEntity<List<AllNetwork>> getProductDetailsByBrandIds(@PathVariable("id") int productId) {
        List<AllNetwork> productList = allNetworkService.getAllNetworkByProductId(productId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


//




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
            @RequestParam(required = false) Double regularprice,
            @RequestParam(required = false) Integer warranty,
            @RequestParam(required = false) String portside,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String catagoryName,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String productItemName,

            @RequestParam(required = false) String mimotechnology,
            @RequestParam(required = false) String vpnsupport,
            @RequestParam(required = false) String wificoveragerange,
            @RequestParam(required = false) String datatransferrate,
            @RequestParam(required = false) String datatransferratewifi,
            @RequestParam(required = false) String numberoflanport,
            @RequestParam(required = false) String numberofwanport,
            @RequestParam(required = false) String wannetworkstandard,
            @RequestParam(required = false) String lannetworkstandard,
            @RequestParam(required = false) String wifigeneration
    ) {
        List<AllNetwork> filteredNetworks = allNetworkService.FilterAllNetwork(
                regularprice, warranty, portside, color, catagoryName, productName, brandName, productItemName,
                mimotechnology, vpnsupport, wificoveragerange, datatransferrate, datatransferratewifi,
                numberoflanport, numberofwanport, wannetworkstandard, lannetworkstandard, wifigeneration
        );
        return ResponseEntity.ok(filteredNetworks);
    }

}
