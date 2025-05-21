
package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AddToCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddToCartRepository extends JpaRepository<AddToCart, Integer> {
    List<AddToCart> findByUserId(Long userId);

    //Add To Card tebele Remove then Oreder By User ID
    void deleteAllByUser_Id(Long userId);


// Add To Card Tebele Remove then CC Builder Item Tebele
//    void deleteByCcitemId(int ccitemsId);


}