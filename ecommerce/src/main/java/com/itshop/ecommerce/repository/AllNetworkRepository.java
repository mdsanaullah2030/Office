package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.entity.AllPrinter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllNetworkRepository extends JpaRepository<AllNetwork, Integer> {


    @Query("SELECT a FROM AllNetwork a WHERE " +
            "(:regularprice IS NULL OR a.regularprice = :regularprice) AND " +
            "(:warranty IS NULL OR a.warranty = :warranty) AND " +
            "(:portside   IS NULL OR a.portside   = :portside  ) AND " +
            "(:color IS NULL OR a.color = :color) AND " +
            "(:catagoryName IS NULL OR a.catagory.name = :catagoryName) AND " +
            "(:productName IS NULL OR a.product.name = :productName) AND " +
            "(:brandName IS NULL OR a.brand.brandname = :brandName) AND " +
            "(:productItemName IS NULL OR a.productItem.productitemname = :productItemName)")
    List<AllNetwork> filterAllNetwork(
            @Param("portside")String portside,
            @Param("color") String color,
            @Param("regularprice") Double regularprice,
            @Param("warranty") Integer warranty,
            @Param("catagoryName") String catagoryName,
            @Param("productName") String productName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName
    );




}
