package com.saverfavor.capnest.repository;

import com.saverfavor.capnest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
