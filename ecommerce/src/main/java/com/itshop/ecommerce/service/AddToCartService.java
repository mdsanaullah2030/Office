
package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
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
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private PcForPartAddRepository pcForPartAddRepository;

    @Autowired
    private CCBuilderItemDitelsRepository ccBuilderItemDitelsRepository;


    @Autowired
    private ItemRepository itemRepository;


    public AddToCart getById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductItem not found with id: " + id));
    }



/// Product Details Add To Cart

    @Transactional
    public AddToCart productDetailsaddToCart(Long userId, int productDetailsId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        ProductDetails productDetails = productDetailsRepository.findById(productDetailsId).orElseThrow();

        double price = (productDetails.getSpecialprice() > 0)
                ? productDetails.getSpecialprice()
                : productDetails.getRegularprice();

        double itemPrice = price * quantity;

        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setProductDetails(productDetails);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(itemPrice);

        // Step 1: Save the new cart item
        AddToCart savedItem = cartRepository.save(cartItem);

        // Step 2: Fetch all cart items for this user
        List<AddToCart> allCartItems = cartRepository.findByUserId(userId);

        // Step 3: Calculate the total price for this user
        double total = allCartItems.stream()
                .mapToDouble(AddToCart::getPrice)
                .sum();

        // Step 4: Set the calculated totalprice in the newly added item
        savedItem.setTotalprice((int) total); // casting to int as your entity uses `int`
        return cartRepository.save(savedItem); // Save again to update the totalprice field
    }



    /// PC For Part  Add To Cart

    @Transactional
    public AddToCart addPcPartToCart(Long userId, int pcPartId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        PcForPartAdd part = pcForPartAddRepository.findById(pcPartId).orElseThrow();

        double price = (part.getSpecialprice() > 0)
                ? part.getSpecialprice()
                : part.getRegularprice();

        double itemPrice = price * quantity;

        // Create cart item
        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setPcForPartAdd(part);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(itemPrice);

        // Save the item first
        AddToCart savedCartItem = cartRepository.save(cartItem);

        // Fetch all cart items for the user to calculate total
        List<AddToCart> userCartItems = cartRepository.findByUserId(userId);

        double totalPrice = userCartItems.stream()
                .mapToDouble(AddToCart::getPrice)
                .sum();

        // Set the total price in the latest saved item
        savedCartItem.setTotalprice((int) totalPrice); // cast to int for your entity
        return cartRepository.save(savedCartItem);
    }






    /// CC Item Bulder  Add To Cart *********&&&&&&&

    @Transactional
    public AddToCart CCItemBuilderAddToCart(Long userId, int CCItemBulderId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        CCBuilderItemDitels part = ccBuilderItemDitelsRepository.findById(CCItemBulderId).orElseThrow();

        double pricePerItem = (part.getSpecialprice() > 0) ? part.getSpecialprice() : part.getRegularprice();
        double newCartItemPrice = pricePerItem * quantity;

        // Create new cart item
        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setCcBuilderItemDitels(part);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(newCartItemPrice);

        // Save the new cart item first (optional: you can do this after totalprice too)
        AddToCart savedCart = cartRepository.save(cartItem);

        // Calculate total price of all cart items for this user
        List<AddToCart> userCartItems = cartRepository.findByUserId(userId);
        double totalPrice = userCartItems.stream()
                .mapToDouble(AddToCart::getPrice)
                .sum();

        // Set total price in the latest saved cart item
        savedCart.setTotalprice((int) totalPrice); // cast to int if needed
        return cartRepository.save(savedCart);
    }



    public List<AddToCart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }
}