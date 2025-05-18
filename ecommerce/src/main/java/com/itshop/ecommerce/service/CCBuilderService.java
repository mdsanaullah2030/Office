package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.AboutUs;
import com.itshop.ecommerce.entity.CCBuilder;
import com.itshop.ecommerce.repository.CCBuilderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CCBuilderService {

    @Autowired
    private CCBuilderRepository ccBuilderRepository;

    public List<CCBuilder> getAll() {
        return ccBuilderRepository.findAll();
    }

    public Optional<CCBuilder> getById(int id) {
        return ccBuilderRepository.findById(id);
    }

    public CCBuilder create(CCBuilder ccBuilder) {
        return ccBuilderRepository.save(ccBuilder);
    }


    public CCBuilder update(int id, CCBuilder updated) {
        try {
            return ccBuilderRepository.findById(id).map(existing -> {
                existing.setName(updated.getName());
                return ccBuilderRepository.save(existing);
            }).orElseThrow(() -> new RuntimeException("CCBuilder with ID " + id + " not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error updating CCBuilder: " + e.getMessage());
        }
    }








    public void delete(int id) {
        ccBuilderRepository.deleteById(id);
    }


}
