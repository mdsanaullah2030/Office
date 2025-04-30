package com.itshop.ecommerce.repository;


import com.itshop.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.catagory.name = :catagoryName")
    List<Product> findProductsByCatagoryName(@Param("catagoryName") String catagoryName);

    @Query("SELECT p FROM Product p WHERE p.catagory.id=:catagoryid")
    List<Product>findProductsByCatagoryId(@Param("catagoryid") int catagoryid);
}

