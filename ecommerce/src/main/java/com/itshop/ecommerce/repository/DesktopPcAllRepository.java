package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.DesktopPcAll;
import com.itshop.ecommerce.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesktopPcAllRepository extends JpaRepository<DesktopPcAll,Integer> {



    @Query("SELECT d FROM DesktopPcAll d WHERE " +
            "(:processorbrand IS NULL OR d.processorbrand = :processorbrand) AND " +
            "(:generation IS NULL OR d.generation = :generation) AND " +
            "(:processortype IS NULL OR d.processortype = :processortype) AND " +
            "(:warranty IS NULL OR d.warranty = :warranty) AND " +
            "(:displaysizerange IS NULL OR d.displaysizerange = :displaysizerange) AND " +
            "(:ram IS NULL OR d.ram = :ram) AND " +
            "(:graphicsmemory IS NULL OR d.graphicsmemory = :graphicsmemory) AND " +
            "(:operatingsystem IS NULL OR d.operatingsystem = :operatingsystem) AND " +
            "(:color IS NULL OR d.color = :color) AND " +
            "(:catagoryName IS NULL OR d.catagory.name = :catagoryName) AND " +
            "(:productName IS NULL OR d.product.name = :productName) AND " +
            "(:brandName IS NULL OR d.brand.brandname = :brandName) AND " +
            "(:productItemName IS NULL OR d.productItem.productitemname = :productItemName) AND " +
            "(:regularprice IS NULL OR d.regularprice = :regularprice)")
    List<DesktopPcAll> filterDesktopProducts(
            @Param("processorbrand") String processorbrand,
            @Param("generation") String generation,
            @Param("processortype") String processortype,
            @Param("warranty") Integer warranty,
            @Param("displaysizerange") String displaysizerange,
            @Param("ram") String ram,
            @Param("graphicsmemory") String graphicsmemory,
            @Param("operatingsystem") String operatingsystem,
            @Param("color") String color,
            @Param("catagoryName") String catagoryName,
            @Param("productName") String productName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName,
            @Param("regularprice") Double regularprice
    );
}
