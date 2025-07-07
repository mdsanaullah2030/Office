package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.AddToCart;
import com.ecommerce.brandlyandco.entity.Order;
import com.ecommerce.brandlyandco.entity.Product;
import com.ecommerce.brandlyandco.entity.User;
import com.ecommerce.brandlyandco.repository.AddToCartRepository;
import com.ecommerce.brandlyandco.repository.OrderRepository;
import com.ecommerce.brandlyandco.repository.ProductRepository;
import com.ecommerce.brandlyandco.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddToCartRepository addToCartRepository;

    @Autowired
    private EmailService emailService;

    //Product Details Order


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }




    public Order saveOrder(Order order) {
        // Validate user
        if (order.getUser() == null || order.getUser().getId() == 0) {
            throw new RuntimeException("User information is missing.");
        }

        // Validate product list
        if (order.getProductList() == null || order.getProductList().isEmpty()) {
            throw new RuntimeException("No product selected.");
        }

        Product requestProduct = order.getProductList().get(0); // Support one product
        Product product = productRepository.findById(requestProduct.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validate quantity
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getModel());
        }

        // Calculate price
        double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
        double totalPrice = unitPrice * order.getQuantity();

        // Set order details
        order.setProductid(product.getModel());
        order.setProductname(product.getProductname());
        order.setPrice(totalPrice);
        order.setStatus("PENDING");
        order.setProductList(List.of(product));

        // Update product stock
        product.setQuantity(product.getQuantity() - order.getQuantity());
        productRepository.save(product);

        return orderRepository.save(order);
    }



    //Add To Cart All Order &&&&&&&&&
    @Transactional
    public Order saveAllAddToCartOrder(Long userId, String districts, String upazila, String address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AddToCart> cartItems = addToCartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found for user ID: " + userId);
        }

        Order order = new Order();
        order.setUser(user);
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhonenumber(user.getPhoneNo());
        order.setDistricts(districts);
        order.setUpazila(upazila);
        order.setAddress(address);
        order.setStatus("PENDING");
        order.setProductname("Multiple Items");
        order.setProductid("Multiple");

        double totalPrice = 0;

        List<Product> productList = new ArrayList<>();



        for (AddToCart item : cartItems) {
            totalPrice += item.getPrice();

            if (item.getProduct() != null) {
                Product pd = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                pd.setQuantity(pd.getQuantity() - item.getQuantity());
                productRepository.save(pd);
                productList.add(pd);
            }

        }

        order.setPrice(totalPrice);
        order.setProductList(productList);


        Order savedOrder = orderRepository.save(order);

        addToCartRepository.deleteAllByUser_Id(userId);

        return savedOrder;
    }

//All Laptoap Order





    @Transactional
    public void updateOrder(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);

        // Prepare email message
        String to = order.getEmail();
        String subject = "Order Status Updated";
        String text = "Hello " + order.getName() + ",\n\n"
                + "Your order with ID " + order.getId() + " has been updated to the following status:\n\n"
                + "**" + newStatus + "**\n\n"
                + "Thank you for shopping with us.\n\n"
                + "Best regards,\nIT Shop Team";

        try {
            emailService.sendSimpleEmail(to, subject, text);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }


    // OrderService.java
    @Transactional
    public String deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Restore stock for all associated ProductDetails
            if (order.getProductList() != null) {
                for (Product product : order.getProductList()) {
                    product.setQuantity(product.getQuantity() + order.getQuantity());
                    productRepository.save(product);
                }
            }

            orderRepository.deleteById(id);
            return "Order deleted successfully.";
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


    //User Id order data get//
    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }

}
