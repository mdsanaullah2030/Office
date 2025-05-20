package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;



    @Autowired
    private OrderRepository orderRepository;

   @Autowired
   private AddToCartRepository addToCartRepository;

   @Autowired
   private PcForPartAddRepository pcForPartAddRepository;

   @Autowired
   private CCBuilderItemDitelsRepository ccBuilderItemDitelsRepository;





    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }


    //User Id order data get//
    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }


//Product Details Order

    public Order saveOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            }) ;
        }

        // Set ProductDetails and calculate price
        if (order.getProductDetails() != null && order.getProductDetails().getId() != 0) {
            productDetailsRepository.findById(order.getProductDetails().getId()).ifPresent(productDetails -> {
                order.setProductDetails(productDetails);
                order.setProductid(productDetails.getProductid());
                order.setProductname(productDetails.getName());
                order.setStatus("PENDING");


                // Calculate price
                double unitPrice = productDetails.getSpecialprice() > 0
                        ? productDetails.getSpecialprice()
                        : productDetails.getRegularprice();
                int orderQuantity = order.getQuantity();
                order.setPrice(unitPrice * orderQuantity);

                // Decrease stock quantity
                int remainingQuantity = productDetails.getQuantity() - orderQuantity;
                productDetails.setQuantity(remainingQuantity);
                productDetailsRepository.save(productDetails);
            });
        }

        return orderRepository.save(order);
    }




//PC Part Order


    public Order PcForPartOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        // Set PcForPartAdd details and calculate price
        if (order.getPcForPartAdd() != null && order.getPcForPartAdd().getId() != 0) {
            pcForPartAddRepository.findById(order.getPcForPartAdd().getId()).ifPresent(pcPart -> {

                int orderedQty = order.getQuantity();
                int availableQty = pcPart.getQuantity();

                // Check if enough quantity exists
                if (availableQty < orderedQty) {
                    throw new RuntimeException("Not enough quantity available for product: " + pcPart.getName());
                }

                // Set product-related fields in order
                order.setPcForPartAdd(pcPart);
                order.setProductid(String.valueOf(pcPart.getId()));
                order.setProductname(pcPart.getName());
                order.setStatus("PENDING");

                // Set price based on specialprice or regularprice
                double unitPrice = pcPart.getSpecialprice() > 0 ? pcPart.getSpecialprice() : pcPart.getRegularprice();
                order.setPrice(unitPrice * orderedQty);

                // Update available quantity in PcForPartAdd
                pcPart.setQuantity(availableQty - orderedQty);
                pcForPartAddRepository.save(pcPart);
            });
        }

        return orderRepository.save(order);
    }




    //CC Item Bulder Order **********


    public Order CCItemBulderOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        // Set PcForPartAdd details and calculate price
        if (order.getCcBuilderItemDitels() != null && order.getCcBuilderItemDitels().getId() != 0) {
            ccBuilderItemDitelsRepository.findById(order.getCcBuilderItemDitels().getId()).ifPresent(pcPart -> {

                int orderedQty = order.getQuantity();
                int availableQty = pcPart.getQuantity();

                // Check if enough quantity exists
                if (availableQty < orderedQty) {
                    throw new RuntimeException("Not enough quantity available for product: " + pcPart.getName());
                }

                // Set product-related fields in order
                order.setCcBuilderItemDitels(pcPart);
                order.setProductid(String.valueOf(pcPart.getId()));
                order.setProductname(pcPart.getName());
                order.setStatus("PENDING");

                // Set price based on specialprice or regularprice
                double unitPrice = pcPart.getSpecialprice() > 0 ? pcPart.getSpecialprice() : pcPart.getRegularprice();
                order.setPrice(unitPrice * orderedQty);

                // Update available quantity in PcForPartAdd
                pcPart.setQuantity(availableQty - orderedQty);
                ccBuilderItemDitelsRepository.save(pcPart);
            });
        }

        return orderRepository.save(order);
    }










////Add To Cart Pc Part Order

@Transactional
public Order saveOrderFromCartAndPcPart(
        int userId,
        int addToCartId,
        int pcForPartAddId,
        String districts,
        String upazila,
        String address
) {
    User user = userRepository.findById((long) userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    AddToCart cart = addToCartRepository.findById(addToCartId)
            .orElseThrow(() -> new RuntimeException("AddToCart entry not found"));

    PcForPartAdd pcPart = pcForPartAddRepository.findById(pcForPartAddId)
            .orElseThrow(() -> new RuntimeException("PcForPartAdd not found"));

    if (pcPart.getQuantity() < cart.getQuantity()) {
        throw new RuntimeException("Not enough quantity available for: " + pcPart.getName());
    }

    // Create new Order
    Order order = new Order();
    order.setUser(user);
    order.setName(user.getName());
    order.setEmail(user.getEmail());
    order.setPhoneNo(user.getPhoneNo());

    order.setAddToCart(cart);
    order.setPcForPartAdd(pcPart);
    order.setProductid(String.valueOf(pcPart.getId()));
    order.setProductname(pcPart.getName());
    order.setQuantity(cart.getQuantity());
    order.setPrice(cart.getPrice());
    order.setStatus("PENDING");

    //  Set Address Fields
    order.setDistricts(districts);
    order.setUpazila(upazila);
    order.setAddress(address);

    // Subtract quantity
    pcPart.setQuantity(pcPart.getQuantity() - cart.getQuantity());
    pcForPartAddRepository.save(pcPart);

    return orderRepository.save(order);
}







///Add To cart  ProductDetails  saveOrder

    @Transactional
    public Order saveOrderProductDetails(
            int userId,
            int addToCartId,
            int ProductDetails,
            String districts,
            String upazila,
            String address
    ) {
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddToCart cart = addToCartRepository.findById(addToCartId)
                .orElseThrow(() -> new RuntimeException("AddToCart entry not found"));

        ProductDetails productDetails = productDetailsRepository.findById(ProductDetails)
                .orElseThrow(() -> new RuntimeException("PcForPartAdd not found"));

        if (productDetails.getQuantity() < cart.getQuantity()) {
            throw new RuntimeException("Not enough quantity available for: " + productDetails.getName());
        }

        // Create new Order
        Order order = new Order();
        order.setUser(user);
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhoneNo(user.getPhoneNo());

        order.setAddToCart(cart);
        order.setProductDetails(productDetails);
        order.setProductid(String.valueOf(productDetails.getId()));
        order.setProductname(productDetails.getName());
        order.setQuantity(cart.getQuantity());
        order.setPrice(cart.getPrice());
        order.setStatus("PENDING");

        //  Set Address Fields
        order.setDistricts(districts);
        order.setUpazila(upazila);
        order.setAddress(address);

        // Subtract quantity
        productDetails.setQuantity(productDetails.getQuantity() - cart.getQuantity());
        productDetailsRepository.save(productDetails);

        return orderRepository.save(order);
    }


//Add To CC Item Builder Order &&&&&&&&&


    @Transactional
    public Order saveCCItemBuilderOrder (

            int userId,
            int addToCartId,
            int CCBuilderItemDitels,
            String districts,
            String upazila,
            String address
    ) {
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AddToCart cart = addToCartRepository.findById(addToCartId)
                .orElseThrow(() -> new RuntimeException("AddToCart entry not found"));

        CCBuilderItemDitels ccBuilderItemDitels = ccBuilderItemDitelsRepository.findById(CCBuilderItemDitels)
                .orElseThrow(() -> new RuntimeException("PcForPartAdd not found"));

        if (ccBuilderItemDitels.getQuantity() < cart.getQuantity()) {
            throw new RuntimeException("Not enough quantity available for: " + ccBuilderItemDitels.getName());
        }

        // Create new Order
        Order order = new Order();
        order.setUser(user);
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhoneNo(user.getPhoneNo());

        order.setAddToCart(cart);
        order.setCcBuilderItemDitels(ccBuilderItemDitels);
        order.setProductid(String.valueOf(ccBuilderItemDitels.getId()));
        order.setProductname(ccBuilderItemDitels.getName());
        order.setQuantity(cart.getQuantity());
        order.setPrice(cart.getPrice());
        order.setStatus("PENDING");

        //  Set Address Fields
        order.setDistricts(districts);
        order.setUpazila(upazila);
        order.setAddress(address);

        // Subtract quantity
        ccBuilderItemDitels.setQuantity(ccBuilderItemDitels.getQuantity() - cart.getQuantity());
        ccBuilderItemDitelsRepository.save(ccBuilderItemDitels);

        return orderRepository.save(order);
    }














///Update Order Statas


    @Transactional
    public void updateOrderActions(int orderId, String newstatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newstatus); // Only updating actions
        orderRepository.save(order); // Other fields remain unchanged
    }





    public String deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Optional: Restore stock before deleting
            ProductDetails product = order.getProductDetails();
            if (product != null) {
                product.setQuantity(product.getQuantity() + order.getQuantity());
                productDetailsRepository.save(product);
            }

            orderRepository.deleteById(id);
            return "Order deleted successfully.";
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }


}
