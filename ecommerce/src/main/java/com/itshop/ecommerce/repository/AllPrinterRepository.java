package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.entity.AllPrinter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllPrinterRepository extends JpaRepository<AllPrinter, Integer> {

    //Catagory By ID
    List<AllPrinter> findByCatagoryId(int catagoryId);

    ///Brand BY ID
    List<AllPrinter> findByBrandId(int brandId);



    // find By Product Id
    List<AllPrinter>findByProductId(int productId) ;



    @Query("SELECT a FROM AllPrinter a WHERE " +
            "(:type IS NULL OR a.type = :type) AND " +
            "(:printspeed IS NULL OR a.printspeed = :printspeed) AND " +
            "(:printwidth IS NULL OR a.printwidth = :printwidth) AND " +
            "(:printresolution IS NULL OR a.printresolution = :printresolution) AND " +
            "(:interfaces IS NULL OR a.interfaces = :interfaces) AND " +
            "(:bodycolor IS NULL OR a.bodycolor = :bodycolor) AND " +
            "(:regularprice IS NULL OR a.regularprice = :regularprice) AND " +
            "(:warranty IS NULL OR a.warranty = :warranty) AND " +
            "(:catagoryName IS NULL OR a.catagory.name = :catagoryName) AND " +
            "(:productName IS NULL OR a.product.name = :productName) AND " +
            "(:brandName IS NULL OR a.brand.brandname = :brandName) AND " +
            "(:productItemName IS NULL OR a.productItem.productitemname = :productItemName)")
    List<AllPrinter> filterAllPrinters(
            @Param("type") String type,
            @Param("printspeed") String printspeed,
            @Param("printwidth") String printwidth,
            @Param("printresolution") String printresolution,
            @Param("interfaces") String interfaces,
            @Param("bodycolor") String bodycolor,
            @Param("regularprice") Double regularprice,
            @Param("warranty") Integer warranty,
            @Param("catagoryName") String catagoryName,
            @Param("productName") String productName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName
    );

}
