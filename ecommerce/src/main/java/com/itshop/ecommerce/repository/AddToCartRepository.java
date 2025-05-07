
package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AddToCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddToCartRepository extends JpaRepository<AddToCart, Integer> {
    List<AddToCart> findByUserId(Long userId);
}