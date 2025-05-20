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

//Catagory By ID
    List<ProductDetails> findByCatagoryId(int catagoryId);

///Brand BY ID
    List<ProductDetails> findByBrandId(int brandId);



// find By Product Id
 List<ProductDetails>findByProductId(int productId) ;



//Product Details BY ID
    Optional<ProductDetails> findById(Integer id);

 //  Product Details BY Name
    Optional<ProductDetails> findByName(String name);

    //Wareenty




////Filter
@Query("SELECT p FROM ProductDetails p " +
        "WHERE (:brandname IS NULL OR p.brand.brandname = :brandname) " +
        "AND (:productName IS NULL OR p.product.name = :productName) " +
        "AND (:regularPrice IS NULL OR p.regularprice = :regularPrice) " +
        "AND (:warranty IS NULL OR p.warranty = :warranty)")
List<ProductDetails> filterByBrandnameAndProductNameAndRegularPriceAndWarranty(
        @Param("brandname") String brandname,
        @Param("productName") String productName,
        @Param("regularPrice") Double regularPrice,
        @Param("warranty") Integer warranty
);





}