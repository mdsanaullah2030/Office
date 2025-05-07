package com.itshop.ecommerce.repository;


import com.itshop.ecommerce.entity.PcForPartAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcForPartAddRepository extends JpaRepository<PcForPartAdd,Integer> {
}

