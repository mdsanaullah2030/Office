package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {

    // Custom query method to find ProductItems by product ID
    @Query("SELECT pi FROM ProductItem pi WHERE pi.product.id = :productId")
    List<ProductItem> getItemsByProduct(@Param("productId") int productId);

}
