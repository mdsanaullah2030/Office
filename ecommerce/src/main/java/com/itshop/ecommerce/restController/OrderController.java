package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.Order;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import com.itshop.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //  Save Order
    @PostMapping("/api/orders/save")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(savedOrder);
    }



    //   PcForPartOrder"
//    @PostMapping("/api/pcforpartorder/save")
//    public ResponseEntity<Order> savePcForPartOrder(@RequestBody Order order) {
//        Order savedOrder = orderService.PcForPartOrder(order);
//        return ResponseEntity.ok(savedOrder);
//    }



    @PostMapping("/api/pcpartorder/save")
    public ResponseEntity<Order> savePcForPartOrder(@RequestBody Order order) {
        Order savedOrder = orderService.PcForPartOrder(order);
        return ResponseEntity.ok(savedOrder);
    }




    @PostMapping("/api/orders/cartpcpart/save")

    public ResponseEntity<Order> createOrderFromCartAndPart(
            @RequestParam int userId,
            @RequestParam int addToCartId,
            @RequestParam int pcForPartAddId,
            @RequestBody Map<String, String> addressData
    ) {
        String districts = addressData.getOrDefault("districts", "");
        String upazila = addressData.getOrDefault("upazila", "");
        String address = addressData.getOrDefault("address", "");

        Order order = orderService.saveOrderFromCartAndPcPart(
                userId,
                addToCartId,
                pcForPartAddId,
                districts,
                upazila,
                address
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }




///Add To Card product details  Order

    @PostMapping("/api/orders/cadordreproductdetails/save")

    public ResponseEntity<Order> createOrderCartproductDetailsId(
            @RequestParam int userId,
            @RequestParam int addToCartId,
            @RequestParam int productDetailsId,
            @RequestBody Map<String, String> addressData
    ) {
        String districts = addressData.getOrDefault("districts", "");
        String upazila = addressData.getOrDefault("upazila", "");
        String address = addressData.getOrDefault("address", "");

        //  Correct service method here
        Order order = orderService.saveOrderProductDetails(
                userId,
                addToCartId,
                productDetailsId,
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



    // Delete order
    @DeleteMapping("/api/orders/delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
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

}
