package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails,Integer>
{


    List<ProductDetails> findByCatagoryId(int catagoryId);


    List<ProductDetails> findByBrandId(int brandId);


    Optional<ProductDetails> findById(Integer id);
    Optional<ProductDetails> findByName(String name);




    @Query("SELECT p FROM ProductDetails p " +
            "WHERE (:brandname IS NULL OR p.brand.brandname = :brandname) " +
            "AND (:productName IS NULL OR p.product.name = :productName) " +
            "AND (:regularPrice IS NULL OR p.regularprice = :regularPrice)")
    List<ProductDetails> filterByBrandnameAndProductNameAndRegularPrice(
            @Param("brandname") String brandname,
            @Param("productName") String productName,
            @Param("regularPrice") Double regularPrice
    );




}