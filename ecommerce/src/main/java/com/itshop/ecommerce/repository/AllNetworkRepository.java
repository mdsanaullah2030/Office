package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.AllNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllNetworkRepository extends JpaRepository<AllNetwork, Integer> {
}
