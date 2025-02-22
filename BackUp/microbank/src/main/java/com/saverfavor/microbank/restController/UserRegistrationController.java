package com.saverfavor.microbank.restController;

import com.saverfavor.microbank.entity.UserRegistration;
import com.saverfavor.microbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/UserRegistration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    //All Data get//
    @GetMapping("/get")
    public List<UserRegistration> getAllUsers() {
        return userService.getAllUserRegistrations();
    }



    //Data Save//
    @PostMapping("/save")
    public ResponseEntity<String> addUser(
            @RequestPart(value = "userRegistration") UserRegistration userRegistration,
            @RequestParam(value = "image", required = true) MultipartFile imageFile)

            throws IOException {

        userService.saveRegistration(userRegistration, imageFile);
        return new ResponseEntity<>("Registration saved successfully with images", HttpStatus.OK);
    }





    //Data Update //
@PutMapping("/update/{id}")
public ResponseEntity<String>updateUserRegistation(
        @PathVariable int id,
        @RequestPart ("userRegistration")UserRegistration userRegistration,
        @RequestParam(value = "image",required = true)MultipartFile imageFile
//        @RequestParam (value="images" ,required = true)MultipartFile imagesFile
    ) throws IOException{
    try{
        userService.updateUserRegistation(id, userRegistration, imageFile);
        return ResponseEntity.ok("User Registration updated successfully");
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Registration not found with this ID");
    }

  }




}


