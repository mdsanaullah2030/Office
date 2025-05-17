package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Brand;
import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElse(null);
    }



    public Brand updateBrand(int id, Brand updatedBrand) {
        return brandRepository.findById(id).map(c -> {
            c.setBrandname(updatedBrand.getBrandname());
            return brandRepository.save(c);
        }).orElse(null);
    }





    public void deleteBrand(int id) {
        brandRepository.deleteById(id);
    }
}
