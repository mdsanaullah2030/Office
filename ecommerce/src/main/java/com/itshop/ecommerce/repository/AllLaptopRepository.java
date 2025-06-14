package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllLaptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllLaptopRepository extends JpaRepository<AllLaptop, Integer> {

    @Query("SELECT a FROM AllLaptop a WHERE " +

            "(:generation IS NULL OR a.generation = :generation) AND " +
            "(:processortype IS NULL OR a.processortype = :processortype) AND " +
            "(:warranty IS NULL OR a.warranty = :warranty) AND " +
            "(:displaysizerange IS NULL OR a.displaysizerange = :displaysizerange) AND " +
            "(:ram IS NULL OR a.ram = :ram) AND " +
            "(:graphicsmemory IS NULL OR a.graphicsmemory = :graphicsmemory) AND " +
            "(:operatingsystem IS NULL OR a.operatingsystem = :operatingsystem) AND " +
            "(:color IS NULL OR a.color = :color) AND " +
            "(:weightrange IS NULL OR a.weightrange = :weightrange) AND " +
            "(:fingerprintsensor IS NULL OR a.fingerprintsensor = :fingerprintsensor) AND " +
            "(:lan IS NULL OR a.lan = :lan) AND " +
            "(:graphicschipset IS NULL OR a.graphicschipset = :graphicschipset) AND " +
            "(:maxramsupport IS NULL OR a.maxramsupport = :maxramsupport) AND " +
            "(:touchscreen IS NULL OR a.touchscreen = :touchscreen) AND " +
            "(:displayresolutionrange IS NULL OR a.displayresolutionrange = :displayresolutionrange) AND " +
            "(:catagoryName IS NULL OR a.catagory.name = :catagoryName) AND " +
            "(:productName IS NULL OR a.product.name = :productName) AND " +
            "(:brandName IS NULL OR a.brand.brandname = :brandName) AND " +
            "(:productItemName IS NULL OR a.productItem.productitemname = :productItemName)")
    List<AllLaptop> filterAllLaptops(

            @Param("generation") String generation,
            @Param("processortype") String processortype,
            @Param("warranty") Integer warranty,
            @Param("displaysizerange") String displaysizerange,
            @Param("ram") String ram,
            @Param("graphicsmemory") String graphicsmemory,
            @Param("operatingsystem") String operatingsystem,
            @Param("color") String color,
            @Param("weightrange") String weightrange,
            @Param("fingerprintsensor") Boolean fingerprintsensor,
            @Param("lan") Boolean lan,
            @Param("graphicschipset") String graphicschipset,
            @Param("maxramsupport") String maxramsupport,
            @Param("touchscreen") Boolean touchscreen,
            @Param("displayresolutionrange") String displayresolutionrange,
            @Param("catagoryName") String catagoryName,
            @Param("productName") String productName,
            @Param("brandName") String brandName,
            @Param("productItemName") String productItemName
    );
}

