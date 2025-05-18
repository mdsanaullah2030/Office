package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.CCBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CCBuilderRepository extends JpaRepository<CCBuilder, Integer> {
}
