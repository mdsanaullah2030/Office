
package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.AddToCart;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.entity.User;
import com.itshop.ecommerce.repository.AddToCartRepository;
import com.itshop.ecommerce.repository.ProductDetailsRepository;
import com.itshop.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToCartService {

    @Autowired
    private AddToCartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    public AddToCart addToCart(Long userId, int productDetailsId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        ProductDetails productDetails = productDetailsRepository.findById(productDetailsId).orElseThrow();

        double price = (productDetails.getSpecialprice() > 0)
                ? productDetails.getSpecialprice()
                : productDetails.getRegularprice();

        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setProductDetails(productDetails);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price * quantity);

        return cartRepository.save(cartItem);
    }

    public List<AddToCart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }
}