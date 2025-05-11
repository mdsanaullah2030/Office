
package com.itshop.ecommerce.restController;
import com.itshop.ecommerce.entity.AddToCart;
import com.itshop.ecommerce.service.AddToCartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
@AllArgsConstructor
public class AddToCartController {


    @Autowired
    private AddToCartService cartService;

    @PostMapping("/api/addcart/save")
    public AddToCart addToCart(@RequestParam Long userId,
                               @RequestParam int productDetailsId,
                               @RequestParam int quantity) {
        return cartService.addToCart(userId, productDetailsId, quantity);
    }



    @PostMapping("/api/addcart/save/pcpart")
    public AddToCart addPcPartToCart(@RequestParam Long userId,
                                     @RequestParam int pcPartId,
                                     @RequestParam int quantity) {
        return cartService.addPcPartToCart(userId, pcPartId, quantity);
    }





    @GetMapping("/user/{userId}")
    public List<AddToCart> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @DeleteMapping("/remove/{cartId}")
    public void removeFromCart(@PathVariable int cartId) {
        cartService.removeFromCart(cartId);
    }

}


