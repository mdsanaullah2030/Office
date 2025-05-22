package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Integer> {
}
