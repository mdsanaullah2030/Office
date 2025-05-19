package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.CCBuilderItemDitels;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CCBuilderItemDitelsRepository extends JpaRepository<CCBuilderItemDitels, Integer> {

    // Correct method to find by item.id
//    List<CCBuilderItemDitels> findByItemId(int itemId);

    @Query("SELECT p FROM CCBuilderItemDitels p WHERE p.item.id = :itemId")
    List<CCBuilderItemDitels> findByItemId(@Param("itemId") int itemId);






    // Correct method to find by ccBuilder.id
    List<CCBuilderItemDitels> findByCcBuilder_Id(int ccBuilderId);
}
