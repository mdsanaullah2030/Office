package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Brand;
import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
@RequiredArgsConstructor
public class BrandsController {

    private final BrandService brandService;

    @PostMapping("/api/brands/save")
    public Brand saveBrand(@RequestBody Brand brand) {
        return brandService.saveBrand(brand);
    }

    @GetMapping("/api/brands/get/all")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/api/brands/get/{id}")
    public Brand getBrandById(@PathVariable int id) {
        return brandService.getBrandById(id);
    }



    @PutMapping("/api/brands/update/{id}")
    public Brand updateCatagory(@PathVariable int id, @RequestBody Brand brand) {
        return brandService.updateBrand(id, brand);
    }


    @DeleteMapping("/api/brands/delete/{id}")
    public void deleteBrand(@PathVariable int id) {
        brandService.deleteBrand(id);
    }
}
