package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;



    @Autowired
    private OrderRepository orderRepository;

   @Autowired
   private AddToCartRepository addToCartRepository;

   @Autowired
   private PcForPartAddRepository pcForPartAddRepository;





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



    public Order PcForPartOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        // Set PcForPartAdd details and calculate price
        if (order.getPcForPartAdd() != null && order.getPcForPartAdd().getId() != 0) {
            pcForPartAddRepository.findById(order.getPcForPartAdd().getId()).ifPresent(pcPart -> {
                order.setPcForPartAdd(pcPart);
                order.setProductid(String.valueOf(pcPart.getId())); // or some unique field
                order.setProductname(pcPart.getName());

                // Calculate price
                double unitPrice = pcPart.getSpecialprice() > 0
                        ? pcPart.getSpecialprice()
                        : pcPart.getRegularprice();
                int orderQuantity = order.getQuantity();
                order.setPrice(unitPrice * orderQuantity);

                // Decrease stock quantity
                int remainingQuantity = pcPart.getQuantity() - orderQuantity;
                pcPart.setQuantity(remainingQuantity);
                pcForPartAddRepository.save(pcPart);
            });
        }

        return orderRepository.save(order);
    }



    @Transactional
    public void updateOrderActions(int orderId, String newstatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newstatus); // Only updating actions
        orderRepository.save(order); // Other fields remain unchanged
    }





    public String deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Optional: Restore stock before deleting
            ProductDetails product = order.getProductDetails();
            if (product != null) {
                product.setQuantity(product.getQuantity() + order.getQuantity());
                productDetailsRepository.save(product);
            }

            orderRepository.deleteById(id);
            return "Order deleted successfully.";
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


}
