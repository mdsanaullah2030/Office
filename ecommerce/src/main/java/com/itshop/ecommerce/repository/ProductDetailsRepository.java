package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer>
{


    List<ProductDetails> findByCatagoryId(int catagoryId);

    Optional<ProductDetails> findById(Integer id);
    Optional<ProductDetails> findByName(String name);


}