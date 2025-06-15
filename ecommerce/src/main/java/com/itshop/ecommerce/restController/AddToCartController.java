
package com.itshop.ecommerce.restController;
import com.itshop.ecommerce.entity.AddToCart;
import com.itshop.ecommerce.entity.ProductItem;
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


//Product ditels Add To card
    @PostMapping("/api/productdetails/AddTocart/save")
    public AddToCart addToCart(@RequestParam Long userId,
                               @RequestParam int productDetailsId,
                               @RequestParam int quantity) {
        return cartService.productDetailsaddToCart(userId, productDetailsId, quantity);
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

    //CP Part Add To caRD Save

    @PostMapping("/api/pcforpart/AddToCart/save")
    public AddToCart addPcPartToCart(@RequestParam Long userId,
                                     @RequestParam int pcPartId,
                                     @RequestParam int quantity) {
        return cartService.addPcPartToCart(userId, pcPartId, quantity);
    }



//CCBuilderItemDitels CCItemBulderAddToCart CCItemBulderId




    @PostMapping("/api/ccitembuilder/AddToCart/save")
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

//Laptop AddTo Cart

    @PostMapping("/api/laptop/AddToCart/save")
    public ResponseEntity<?> laptopAddToCart(@RequestParam Long userId,
                                                 @RequestParam int LaptopId,
                                                 @RequestParam int quantity) {
        try {
            AddToCart result = cartService.LaptopAddToCart(userId, LaptopId, quantity);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }


// DeskTop ADD TO Cart desktopAddToCart

    @PostMapping("/api/desktop/AddToCart/save")
    public ResponseEntity<?> deskTopAddToCart(@RequestParam Long userId,
                                             @RequestParam int DesktopId,
                                             @RequestParam int quantity) {
        try {
            AddToCart result = cartService.desktopAddToCart(userId, DesktopId, quantity);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

//Printer AddTo cart

    @PostMapping("/api/printer/AddToCart/save")
    public ResponseEntity<?> printerAddTocart(@RequestParam Long userId,
                                              @RequestParam int PrinterId,
                                              @RequestParam int quantity) {
        try {
            AddToCart result = cartService.PrinterAddTocart(userId, PrinterId, quantity);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

    //  Camera AddTo cart

    @PostMapping("/api/camera/AddToCart/save")
    public ResponseEntity<?> cameraAddTocart(@RequestParam Long userId,
                                              @RequestParam int CameraId,
                                              @RequestParam int quantity) {
        try {
            AddToCart result = cartService.CameraAddTocart(userId, CameraId, quantity);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }

//Network AddTo cart

    @PostMapping("/api/network/AddToCart/save")
    public ResponseEntity<?> networkAddTocart(@RequestParam Long userId,
                                             @RequestParam int NetworkId,
                                             @RequestParam int quantity) {
        try {
            AddToCart result = cartService.NetworkAddTocart(userId, NetworkId, quantity);
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


