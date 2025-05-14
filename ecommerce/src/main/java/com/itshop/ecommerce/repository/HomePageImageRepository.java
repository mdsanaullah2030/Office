package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.HomePageImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomePageImageRepository extends JpaRepository<HomePageImage,Integer> {
}
