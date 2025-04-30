package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.Catagory;
import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.entity.User;
import com.itshop.ecommerce.repository.CatagoryRepository;
import com.itshop.ecommerce.repository.OrderRepository;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import com.itshop.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private CatagoryRepository catagoryRepository;

    @Autowired
    private OrderRepository orderRepository;







    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }


    //User Id order data get//
    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order saveOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            }) ;
        }

        // Set ProductDetails and calculate price
        if (order.getProductDetails() != null && order.getProductDetails().getId() != 0) {
            productDetailsRepository.findById(order.getProductDetails().getId()).ifPresent(productDetails -> {
                order.setProductDetails(productDetails);
                order.setProductid(productDetails.getProductid());
                order.setProductname(productDetails.getName());
                order.setProduct(productDetails.getProduct()); // Set related Product
                order.setCatagory(productDetails.getCatagory()); // Set related Category

                // Calculate price
                double unitPrice = productDetails.getSpecialprice() > 0
                        ? productDetails.getSpecialprice()
                        : productDetails.getRegularprice();
                int orderQuantity = order.getQuantity();
                order.setPrice(unitPrice * orderQuantity);

                // Decrease stock quantity
                int remainingQuantity = productDetails.getQuantity() - orderQuantity;
                productDetails.setQuantity(remainingQuantity);
                productDetailsRepository.save(productDetails);
            });
        }

        return orderRepository.save(order);
    }



}
