package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.AddToCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddToCartRepository extends JpaRepository<AddToCart, Integer> {
    List<AddToCart> findByUserId(Long userId);

    //Add To Card tebele Remove then Oreder By User ID
    void deleteAllByUser_Id(Long userId);





}