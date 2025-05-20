
package com.itshop.ecommerce.restController;
import com.itshop.ecommerce.entity.AddToCart;
import com.itshop.ecommerce.service.AddToCartService;
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

    @PostMapping("/api/productdetails/AddTocart/save")
    public AddToCart addToCart(@RequestParam Long userId,
                               @RequestParam int productDetailsId,
                               @RequestParam int quantity) {
        return cartService.productDetailsaddToCart(userId, productDetailsId, quantity);
    }



    @PostMapping("/api/pcforpart/AddToCart/save")
    public AddToCart addPcPartToCart(@RequestParam Long userId,
                                     @RequestParam int pcPartId,
                                     @RequestParam int quantity) {
        return cartService.addPcPartToCart(userId, pcPartId, quantity);
    }



//CCBuilderItemDitels CCItemBulderAddToCart CCItemBulderId




    @PostMapping("/api/CCItemBuilder/AddToCart/save")
    public ResponseEntity<?> CcbuilderItemDitels(@RequestParam Long userId,
                                                 @RequestParam int CCItemBulderId,
                                                 @RequestParam int quantity) {
        try {
            AddToCart result = cartService.CCItemBuilderAddToCart(userId, CCItemBulderId, quantity);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
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

}


