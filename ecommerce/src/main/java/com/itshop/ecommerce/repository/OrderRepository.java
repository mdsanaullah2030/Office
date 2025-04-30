package com.itshop.ecommerce.repository;

import com.itshop.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

//    //User id all Loan data show//
//    List<Order> findByUserRegistrationId(long userId);

    List<Order> findByUserId(long userId);

}
