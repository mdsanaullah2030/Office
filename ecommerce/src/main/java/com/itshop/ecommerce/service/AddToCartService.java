
package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.AddToCart;
import com.itshop.ecommerce.entity.PcForPartAdd;
import com.itshop.ecommerce.entity.ProductDetails;
import com.itshop.ecommerce.entity.User;
import com.itshop.ecommerce.repository.*;
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

    @Autowired
    private PcForPartAddRepository pcForPartAddRepository;

    @Autowired
    private CCBuilderItemDitelsRepository ccBuilderItemDitelsRepository;


/// Product Details Add To Cart

    public AddToCart productDetailsaddToCart(Long userId, int productDetailsId, int quantity) {
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




    /// PC For Part  Add To Cart

    public AddToCart addPcPartToCart(Long userId, int pcPartId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow();
        PcForPartAdd part = pcForPartAddRepository.findById(pcPartId).orElseThrow();

        double price = (part.getSpecialprice() > 0)
                ? part.getSpecialprice()
                : part.getRegularprice();

        AddToCart cartItem = new AddToCart();
        cartItem.setUser(user);
        cartItem.setPcForPartAdd(part);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(price * quantity);

        return cartRepository.save(cartItem);
    }








    /// CC Item Bulder  Add To Cart *********&&&&&&&

//    public AddToCart ccItemBuilder(Long userId, int ccItemId, int quantity) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        CCBuilderItemDitels part = ccBuilderItemDitelsRepository.findById(ccItemId)
//                .orElseThrow(() -> new RuntimeException("CCBuilderItemDitels not found"));
//
//        double price = (part.getSpecialprice() > 0)
//                ? part.getSpecialprice()
//                : part.getRegularprice();
//
//        AddToCart cartItem = new AddToCart();
//        cartItem.setUser(user);
//        cartItem.setCcBuilderItemDitels(part);
//        cartItem.setQuantity(quantity);
//        cartItem.setPrice(price * quantity);
//
//        return cartRepository.save(cartItem);
//    }









    public List<AddToCart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public void removeFromCart(int cartId) {
        cartRepository.deleteById(cartId);
    }
}