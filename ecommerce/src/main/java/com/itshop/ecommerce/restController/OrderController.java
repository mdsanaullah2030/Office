package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import com.itshop.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductDetailsRepository productDetailsRepository;


    //  Save Product Ditels Order
    @PostMapping("/api/productdetails/orders/save")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }



///api/pcpartorder/save
@PostMapping("/api/pcforpart/orders/save")
public ResponseEntity<?> savePcForPartOrder(@RequestBody Order order) {
    try {
        Order savedOrder = orderService.PcForPartOrder(order);
        return ResponseEntity.ok(savedOrder);
    } catch (Exception ex) {
        // You can log the exception for debugging purposes
        ex.printStackTrace();

        // Return a 400 Bad Request with a meaningful error message
        return ResponseEntity
                .badRequest()
                .body("Failed to save PC-for-Part Order: " + ex.getMessage());
    }
}





    ///api/CC Item Bulder Order/save

    @PostMapping("/api/ccitem/Bulder/orders/save")
    public ResponseEntity<?> CCItemBulder(@RequestBody Order order) {
        try {
            Order savedOrder = orderService.CCItemBulderOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception ex) {
            // You can log the exception for debugging purposes
            ex.printStackTrace();

            // Return a 400 Bad Request with a meaningful error message
            return ResponseEntity
                    .badRequest()
                    .body("Failed to save PC-for-Part Order: " + ex.getMessage());
        }
    }


///Add To Card All Order

    @PostMapping("/api/orders/AddToCart/save/{id}")
    public ResponseEntity<Order> saveOrderProductDetails(
            @RequestParam long userId,
            @RequestBody Map<String, String> addressData
    ) {
        String districts = addressData.getOrDefault("districts", "");
        String upazila = addressData.getOrDefault("upazila", "");
        String address = addressData.getOrDefault("address", "");

        Order order = orderService.saveAllAddToCartOrder(
                userId,
                districts,
                upazila,
                address
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }








    // Update order status
    @PutMapping("/api/orders/updete/{id}")

    public ResponseEntity<String> updateOrderActions(@PathVariable int id, @RequestParam String actions) {
        orderService.updateOrderActions(id, actions);
        return ResponseEntity.ok("Order actions updated successfully.");
    }








    // Get All Orders
    @GetMapping("/api/orders/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }








    //  Get Single Order by ID
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        return orderOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


//  Get User ID Order by Get

    @GetMapping("/api/Order/getByUser/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }




    // Delete order
    @DeleteMapping("/api/orders/delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
    }


}
