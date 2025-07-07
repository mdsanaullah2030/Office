package com.ecommerce.brandlyandco.repository;

import com.ecommerce.brandlyandco.entity.Order;
import com.ecommerce.brandlyandco.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<User> findByEmail(String email);



    List<Order> findByUserId(long userId);
}


