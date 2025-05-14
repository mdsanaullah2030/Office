package com.itshop.ecommerce.repository;


import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.PcBuilder;
import com.itshop.ecommerce.entity.PcForPartAdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcForPartAddRepository extends JpaRepository<PcForPartAdd,Integer> {

    List<PcForPartAdd> findByPcBuilder(PcBuilder pcBuilder);


}

