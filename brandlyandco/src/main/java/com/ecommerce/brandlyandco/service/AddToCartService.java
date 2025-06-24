package com.ecommerce.brandlyandco.service;

import com.ecommerce.brandlyandco.entity.AddToCart;
import com.ecommerce.brandlyandco.entity.Product;
import com.ecommerce.brandlyandco.entity.User;
import com.ecommerce.brandlyandco.repository.AddToCartRepository;

import com.ecommerce.brandlyandco.repository.ProductRepository;
import com.ecommerce.brandlyandco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddToCartService {

    @Autowired
    private AddToCartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public AddToCart getById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductItem not found with id: " + id));
    }


/// Product Details Add To Cart

    @Transactional
    public AddToCart productDetailsaddToCart(Long userId, int productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Product productDetails = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        double price = (productDetails.getSpecialprice() > 0)
                ? productDetails.getSpecialprice()
                : productDetails.getRegularprice();

        double itemPrice = price * quantity;

        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setProduct(productDetails);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(itemPrice);

        // Save the new item
        AddToCart savedItem = cartRepository.save(cartItem);

        // Get all cart items for this user
        List<AddToCart> allCartItems = cartRepository.findByUserId(userId);

        // Calculate total price
        double total = allCartItems.stream()
                .mapToDouble(AddToCart::getPrice)
                .sum();

        savedItem.setTotalprice((int) total);
        return cartRepository.save(savedItem);
    }





    public List<AddToCart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }
}
