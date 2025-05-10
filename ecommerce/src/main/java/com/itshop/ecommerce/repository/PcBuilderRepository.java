package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.PcBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcBuilderRepository extends JpaRepository<PcBuilder,Integer> {


}
