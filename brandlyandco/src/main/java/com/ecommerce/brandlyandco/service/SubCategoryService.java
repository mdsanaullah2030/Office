package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.SubCategory;
import com.ecommerce.brandlyandco.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }




    public Optional<SubCategory> getSubCategoryById(int id) {
        return subCategoryRepository.findById(id);
    }

    public SubCategory updateSubCategory(int id, SubCategory updatedSubCategory) {
        return subCategoryRepository.findById(id).map(existing -> {
            existing.setSubcategoryname(updatedSubCategory.getSubcategoryname());
            existing.setCatagory(updatedSubCategory.getCatagory());
            return subCategoryRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("SubCategory not found with ID " + id));
    }



    public void deleteSubCategory(int id) {
        subCategoryRepository.deleteById(id);
    }
}
