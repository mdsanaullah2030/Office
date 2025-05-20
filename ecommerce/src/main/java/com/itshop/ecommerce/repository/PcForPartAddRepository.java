package com.itshop.ecommerce.repository;


import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.PcForPartAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcForPartAddRepository extends JpaRepository<PcForPartAdd,Integer> {

    @Query("SELECT p FROM PcForPartAdd p WHERE p.pcbuilder.id = :builderId")
    List<PcForPartAdd> findByPcBuilderId(@Param("builderId") int builderId);


    @Query("SELECT p FROM PcForPartAdd p " +
            "WHERE (:regularprice IS NULL OR p.regularprice = :regularprice) " +
            "AND (:warranty IS NULL OR p.warranty = :warranty) " +
            "AND (:pcbuilderId IS NULL OR p.pcbuilder.id = :pcbuilderId)")
    List<PcForPartAdd> filterByPriceWarrantyAndPcbuilder(
            @Param("regularprice") Double regularprice,
            @Param("warranty") Integer warranty,
            @Param("pcbuilderId") Integer pcbuilderId
    );

}

