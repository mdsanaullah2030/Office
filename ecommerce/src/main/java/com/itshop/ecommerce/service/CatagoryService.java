package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.repository.CatagoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatagoryService {

    @Autowired
    private CatagoryRepository catagoryRepository;

    public Catagory saveCatagory(Catagory catagory) {
        return catagoryRepository.save(catagory);
    }

    public List<Catagory> getAllCatagories() {
        return catagoryRepository.findAll();
    }

    public Optional<Catagory> getCatagoryById(int id) {
        return catagoryRepository.findById(id);
    }

    public Catagory updateCatagory(int id, Catagory updatedCatagory) {
        return catagoryRepository.findById(id).map(c -> {
            c.setName(updatedCatagory.getName());
            return catagoryRepository.save(c);
        }).orElse(null);
    }





    public List<Catagory>findCatagoriesByName(String catagoryName){
        return catagoryRepository.findCatagoryName(catagoryName);
    }
}
