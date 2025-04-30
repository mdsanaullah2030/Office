package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatagoryRepository extends JpaRepository<Catagory, Integer> {




    @Query("SELECT l FROM Catagory l WHERE l.name=:catagoryName")
    List<Catagory>findCatagoryName(@Param("catagoryName") String catagoryName);



}
