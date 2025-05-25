package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//Item Tebele ID by get Product tebele data

    @Query("SELECT p FROM Product p WHERE p.item.id = :itemId")
    List<Product> getProductsByItemId(@Param("itemId") Integer itemId);




    ////Filter
    @Query("SELECT p FROM Product p " +
            "WHERE (:color IS NOT NULL AND p.color = :color) " +
            "OR (:size IS NOT NULL AND p.size = :size) " +
            "OR (:regularprice IS NOT NULL AND p.regularprice = :regularprice) " +
            "OR (:offer IS NOT NULL AND p.offer = :offer) " +
            "OR (:fabric IS NOT NULL AND p.fabric = :fabric) " +
            "OR (:itemId IS NOT NULL AND p.item.id = :itemId) " +
            "OR (:subCategoryId IS NOT NULL AND p.subCategory.id = :subCategoryId) " +
            "OR (:categoryId IS NOT NULL AND p.catagory.id = :categoryId)")
    List<Product> filterProducts(
            @Param("color") String color,
            @Param("size") String size,
            @Param("regularprice") Double regularprice,
            @Param("offer") Double offer,
            @Param("fabric") String fabric,
            @Param("itemId") Integer itemId,
            @Param("subCategoryId") Integer subCategoryId,
            @Param("categoryId") Integer categoryId
    );





}
