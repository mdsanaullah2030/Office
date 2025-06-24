package com.ecommerce.brandlyandco.restController;

import com.ecommerce.brandlyandco.entity.AddToCart;
import com.ecommerce.brandlyandco.service.AddToCartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping
@CrossOrigin
@AllArgsConstructor
public class AddToCartController {


    @Autowired
    private AddToCartService cartService;


    //Product ditels Add To card
    @PostMapping("/api/product/AddTocart/save")
    public ResponseEntity<?> addToCart(@RequestParam Long userId,
                                       @RequestParam int productId,
                                       @RequestParam int quantity) {
        try {
            AddToCart cart = cartService.productDetailsaddToCart(userId, productId, quantity);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/api/AddTocart/get/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            AddToCart item = cartService.getById(id);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch item: " + e.getMessage());
        }
    }



    @GetMapping("/api/addcart/user/get/{userId}")
    public List<AddToCart> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @DeleteMapping("/api/addcart/remove/{cartId}")
    public void removeFromCart(@PathVariable int cartId) {
        cartService.removeFromCart(cartId);
    }

    //
}
