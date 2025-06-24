package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllCamera;
import com.itshop.ecommerce.entity.AllLaptop;
import com.itshop.ecommerce.entity.AllNetwork;
import com.itshop.ecommerce.entity.AllPrinter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllNetworkRepository extends JpaRepository<AllNetwork, Integer> {


    //Catagory By ID
    List<AllNetwork> findByCatagoryId(int catagoryId);

    ///Brand BY ID
    List<AllNetwork> findByBrandId(int brandId);



    // find By Product Id
    List<AllNetwork>findByProductId(int productId) ;




    @Query("SELECT a FROM AllNetwork a WHERE " +
            "(:regularprice IS NULL OR a.regularprice = :regularprice) AND " +
            "(:warranty IS NULL OR a.warranty = :warranty) AND " +
            "(:portside IS NULL OR a.portside = :portside) AND " +
            "(:color IS NULL OR a.color = :color) AND " +
            "(:catagoryName IS NULL OR a.catagory.name = :catagoryName) AND " +
            "(:productName IS NULL OR a.product.name = :productName) AND " +
            "(:brandName IS NULL OR a.brand.brandname = :brandName) AND " +
            "(:productItemName IS NULL OR a.productItem.productitemname = :productItemName) AND " +

            "(:mimotechnology IS NULL OR a.mimotechnology = :mimotechnology) AND " +
            "(:vpnsupport IS NULL OR a.vpnsupport = :vpnsupport) AND " +
            "(:wificoveragerange IS NULL OR a.wificoveragerange = :wificoveragerange) AND " +
            "(:datatransferrate IS NULL OR a.datatransferrate = :datatransferrate) AND " +
            "(:datatransferratewifi IS NULL OR a.datatransferratewifi = :datatransferratewifi) AND " +
            "(:numberoflanport IS NULL OR a.numberoflanport = :numberoflanport) AND " +
            "(:numberofwanport IS NULL OR a.numberofwanport = :numberofwanport) AND " +
            "(:wannetworkstandard IS NULL OR a.wannetworkstandard = :wannetworkstandard) AND " +
            "(:lannetworkstandard IS NULL OR a.lannetworkstandard = :lannetworkstandard) AND " +
            "(:wifigeneration IS NULL OR a.wifigeneration = :wifigeneration)")
    List<AllNetwork> filterAllNetwork(
            @Param("regularprice") Double regularprice,
            @Param("warranty") Integer warranty,
            @Param("portside") String portside,
            @Param("color") String color,
            @Param("catagoryName") String catagoryName,
            @Param("productName") String productName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName,

            @Param("mimotechnology") String mimotechnology,
            @Param("vpnsupport") String vpnsupport,
            @Param("wificoveragerange") String wificoveragerange,
            @Param("datatransferrate") String datatransferrate,
            @Param("datatransferratewifi") String datatransferratewifi,
            @Param("numberoflanport") String numberoflanport,
            @Param("numberofwanport") String numberofwanport,
            @Param("wannetworkstandard") String wannetworkstandard,
            @Param("lannetworkstandard") String lannetworkstandard,
            @Param("wifigeneration") String wifigeneration
    );


}
