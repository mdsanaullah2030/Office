package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.Order;
import com.ecommerce.brandlyandco.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class OrderController {

    @Autowired
    private  OrderService orderService;

    @PostMapping("/api/orders/save")
    public ResponseEntity<Order> createOrder(@RequestBody Order order,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(orderService.saveOrder(order, userDetails));
    }

    @GetMapping("/api/orders/get")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


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





    @PutMapping("/api/orders/update/{id}")
    public ResponseEntity<String> updateOrderActions(@PathVariable int id, @RequestParam String actions) {
        orderService.updateOrder(id, actions);
        return ResponseEntity.ok("Order actions updated successfully.");
    }

    @DeleteMapping("/api/orders/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
