
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





    @GetMapping("/api/addcart/user/get/{userId}")
    public List<AddToCart> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @DeleteMapping("/api/addcart/remove/{cartId}")
    public void removeFromCart(@PathVariable int cartId) {
        cartService.removeFromCart(cartId);
    }

}


