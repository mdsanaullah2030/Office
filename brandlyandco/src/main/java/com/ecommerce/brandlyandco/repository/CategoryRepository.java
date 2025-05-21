package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
