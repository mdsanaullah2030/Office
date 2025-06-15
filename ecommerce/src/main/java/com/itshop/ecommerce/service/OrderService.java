package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.*;
import com.itshop.ecommerce.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
   @Autowired
   private EmailService emailService;

   @Autowired
   private AllLaptopRepository allLaptopRepository;


   @Autowired
   private AllCameraRepository allCameraRepository;

   @Autowired
   private AllPrinterRepository allPrinterRepository;


   @Autowired
   private  AllNetworkRepository allNetworkRepository;

   @Autowired
   private DesktopPcAllRepository desktopPcAllRepository;






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
        // 1. Set User Info
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        // 2. Handle ProductDetails by ID
        if (order.getProductDetailsList() != null && !order.getProductDetailsList().isEmpty()) {
            ProductDetails requestProduct = order.getProductDetailsList().get(0); // Support only one product for now
            Optional<ProductDetails> optionalProduct = productDetailsRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            ProductDetails product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields
            order.setProductid(product.getProductid());
            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setProductDetailsList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            productDetailsRepository.save(product);
        } else {
            throw new RuntimeException("ProductDetails list is missing.");
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

        if (order.getPcForPartAddList() != null && !order.getPcForPartAddList().isEmpty()) {
            PcForPartAdd requestProduct = order.getPcForPartAddList().get(0); // Support only one product for now
            Optional<PcForPartAdd> optionalProduct = pcForPartAddRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            PcForPartAdd product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setPcForPartAddList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            pcForPartAddRepository.save(product);
        } else {
            throw new RuntimeException("ProductDetails list is missing.");
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

        if (order.getCcBuilderItemDitelsList() != null && !order.getCcBuilderItemDitelsList().isEmpty()) {
            CCBuilderItemDitels requestProduct = order.getCcBuilderItemDitelsList().get(0); // Support only one product for now
            Optional<CCBuilderItemDitels> optionalProduct = ccBuilderItemDitelsRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            CCBuilderItemDitels product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setCcBuilderItemDitelsList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            ccBuilderItemDitelsRepository.save(product);
        } else {
            throw new RuntimeException("ProductDetails list is missing.");
        }

        return orderRepository.save(order);
    }





    ///All Laptoap Order


    public Order LaptoapOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        if (order.getAllLaptopList() != null && !order.getAllLaptopList().isEmpty()) {
            AllLaptop requestProduct = order.getAllLaptopList().get(0); // Support only one product for now
            Optional<AllLaptop> optionalProduct = allLaptopRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            AllLaptop product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setAllLaptopList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            allLaptopRepository.save(product);
        } else {
            throw new RuntimeException("All Laptop list is missing.");
        }

        return orderRepository.save(order);
    }

    //ALL Printer Order

    public Order PrinterOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        if (order.getAllPrinterList() != null && !order.getAllPrinterList().isEmpty()) {
            AllPrinter requestProduct = order.getAllPrinterList().get(0); // Support only one product for now
            Optional<AllPrinter> optionalProduct = allPrinterRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            AllPrinter product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setAllPrinterList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            allPrinterRepository.save(product);
        } else {
            throw new RuntimeException("All Laptop list is missing.");
        }

        return orderRepository.save(order);
    }


    // All Newtwork Order

    public Order NewtworkOrder(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        if (order.getAllNetworkList() != null && !order.getAllNetworkList().isEmpty()) {
            AllNetwork requestProduct = order.getAllNetworkList().get(0); // Support only one product for now
            Optional<AllNetwork> optionalProduct = allNetworkRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            AllNetwork product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setAllNetworkList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            allNetworkRepository.save(product);
        } else {
            throw new RuntimeException("All Laptop list is missing.");
        }

        return orderRepository.save(order);
    }



//ALL Desktop PC Order

    public Order DesktopPcOrders(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        if (order.getDesktopPcAllList() != null && !order.getDesktopPcAllList().isEmpty()) {
            DesktopPcAll requestProduct = order.getDesktopPcAllList().get(0); // Support only one product for now
            Optional<DesktopPcAll> optionalProduct = desktopPcAllRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            DesktopPcAll product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setDesktopPcAllList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            desktopPcAllRepository.save(product);
        } else {
            throw new RuntimeException("All Laptop list is missing.");
        }

        return orderRepository.save(order);
    }




//All Camera Order

    public Order CameraOrders(Order order) {

        // Set User details
        if (order.getUser() != null && order.getUser().getId() != 0) {
            userRepository.findById(order.getUser().getId()).ifPresent(user -> {
                order.setUser(user);
                order.setName(user.getName());
                order.setEmail(user.getEmail());
                order.setPhoneNo(user.getPhoneNo());
            });
        }

        if (order.getAllCameraList() != null && !order.getAllCameraList().isEmpty()) {
            AllCamera requestProduct = order.getAllCameraList().get(0); // Support only one product for now
            Optional<AllCamera> optionalProduct = allCameraRepository.findById(requestProduct.getId());

            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found.");
            }

            AllCamera product = optionalProduct.get();

            // 3. Validate Quantity
            int orderQuantity = order.getQuantity();
            if (product.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // 4. Calculate Price
            double unitPrice = product.getSpecialprice() > 0 ? product.getSpecialprice() : product.getRegularprice();
            double totalPrice = unitPrice * orderQuantity;

            // 5. Set Order Fields

            order.setProductname(product.getName());
            order.setPrice(totalPrice);
            order.setStatus("PENDING");
            order.setAllCameraList(List.of(product)); // Set actual product

            // 6. Update Product stock
            product.setQuantity(product.getQuantity() - orderQuantity);
            allCameraRepository.save(product);
        } else {
            throw new RuntimeException("All Desktop Pc All list is missing.");
        }

        return orderRepository.save(order);
    }









    //Add To Cart All Order &&&&&&&&&
    @Transactional
    public Order saveAllAddToCartOrder(Long userId, String districts, String upazila, String address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<AddToCart> cartItems = addToCartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found for user ID: " + userId);
        }

        Order order = new Order();
        order.setUser(user);
        order.setName(user.getName());
        order.setEmail(user.getEmail());
        order.setPhoneNo(user.getPhoneNo());
        order.setDistricts(districts);
        order.setUpazila(upazila);
        order.setAddress(address);
        order.setStatus("PENDING");
        order.setProductname("Multiple Items");
        order.setProductid("Multiple");

        double totalPrice = 0;

        List<ProductDetails> productList = new ArrayList<>();
        List<PcForPartAdd> pcPartList = new ArrayList<>();
        List<CCBuilderItemDitels> ccItemList = new ArrayList<>();
        List<AllLaptop> allLaptops = new ArrayList<>();
        List<AllPrinter> allPrinters=new ArrayList<>();
        List<AllCamera> allCameras=new ArrayList<>();
        List<AllNetwork> allNetworks=new ArrayList<>();
        List<DesktopPcAll>desktopPcAlls=new ArrayList<>();



        for (AddToCart item : cartItems) {
            totalPrice += item.getPrice();

            if (item.getProductDetails() != null) {
                ProductDetails pd = productDetailsRepository.findById(item.getProductDetails().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                pd.setQuantity(pd.getQuantity() - item.getQuantity());
                productDetailsRepository.save(pd);
                productList.add(pd);
            }

            if (item.getPcForPartAdd() != null) {
                PcForPartAdd pc = pcForPartAddRepository.findById(item.getPcForPartAdd().getId())
                        .orElseThrow(() -> new RuntimeException("PC Part not found"));
                pc.setQuantity(pc.getQuantity() - item.getQuantity());
                pcForPartAddRepository.save(pc);
                pcPartList.add(pc);
            }

            if (item.getCcBuilderItemDitels() != null) {
                CCBuilderItemDitels cc = ccBuilderItemDitelsRepository.findById(item.getCcBuilderItemDitels().getId())
                        .orElseThrow(() -> new RuntimeException("CC Item not found"));
                cc.setQuantity(cc.getQuantity() - item.getQuantity());
                ccBuilderItemDitelsRepository.save(cc);
                ccItemList.add(cc);
            }
        }

        order.setPrice(totalPrice);
        order.setProductDetailsList(productList);
        order.setPcForPartAddList(pcPartList);
        order.setCcBuilderItemDitelsList(ccItemList);
        order.setAllLaptopList(allLaptops);
        order.setAllPrinterList(allPrinters);
        order.setAllNetworkList(allNetworks);
        order.setAllCameraList(allCameras);
        order.setDesktopPcAllList(desktopPcAlls);

        Order savedOrder = orderRepository.save(order);

        addToCartRepository.deleteAllByUser_Id(userId);

        return savedOrder;
    }

//All Laptoap Order











///Update Order Statas


    @Transactional
    public void updateOrderActions(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);

        // Prepare email message
        String to = order.getEmail();
        String subject = "Order Status Updated";
        String text = "Hello " + order.getName() + ",\n\n"
                + "Your order with ID " + order.getId() + " has been updated to the following status:\n\n"
                + "**" + newStatus + "**\n\n"
                + "Thank you for shopping with us.\n\n"
                + "Best regards,\nIT Shop Team";

        try {
            emailService.sendSimpleEmail(to, subject, text);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }


    // OrderService.java
    @Transactional
    public String deleteOrder(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            // Restore stock for all associated ProductDetails
            if (order.getProductDetailsList() != null) {
                for (ProductDetails product : order.getProductDetailsList()) {
                    product.setQuantity(product.getQuantity() + order.getQuantity());
                    productDetailsRepository.save(product);
                }
            }

            orderRepository.deleteById(id);
            return "Order deleted successfully.";
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }




}







//Delete Order




