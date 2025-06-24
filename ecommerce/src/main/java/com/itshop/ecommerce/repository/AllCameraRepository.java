package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllCamera;
import com.itshop.ecommerce.entity.AllLaptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllCameraRepository extends JpaRepository<AllCamera, Integer> {

    //Catagory By ID
    List<AllCamera> findByCatagoryId(int catagoryId);

    ///Brand BY ID
    List<AllCamera> findByBrandId(int brandId);



    // find By Product Id
    List<AllCamera>findByProductId(int productId) ;





    @Query("SELECT a FROM AllCamera a WHERE " +
            "(:name IS NULL OR a.name LIKE %:name%) AND " +
            "(:totalpixel IS NULL OR a.totalpixel = :totalpixel) AND " +
            "(:displaysize IS NULL OR a.displaysize = :displaysize) AND " +
            "(:digitalzoom IS NULL OR a.digitalzoom = :digitalzoom) AND " +
            "(:opticalzoom IS NULL OR a.opticalzoom = :opticalzoom) AND " +
            "(:regularprice IS NULL OR a.regularprice = :regularprice) AND " +
            "(:specialprice IS NULL OR a.specialprice = :specialprice) AND " +
            "(:warranty IS NULL OR a.warranty = :warranty) AND " +
            "(:catagoryName IS NULL OR a.catagory.name LIKE %:catagoryName%) AND " +
            "(:brandName IS NULL OR a.brand.brandname LIKE %:brandName%) AND " +
            "(:productItemName IS NULL OR a.productItem.productitemname LIKE %:productItemName%)")
    List<AllCamera> filterAllCamera(
            @Param("name") String name,
            @Param("totalpixel") String totalpixel,
            @Param("displaysize") String displaysize,
            @Param("digitalzoom") String digitalzoom,
            @Param("opticalzoom") String opticalzoom,
            @Param("regularprice") Double regularprice,
            @Param("specialprice") Double specialprice,
            @Param("warranty") Integer warranty,
            @Param("catagoryName") String catagoryName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName
    );


}
