package com.itshop.ecommerce.restController;

import com.itshop.ecommerce.entity.ContactUs;
import com.itshop.ecommerce.service.ContactUsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;
//
    @GetMapping("/api/contactus/getall")
    public ResponseEntity<List<ContactUs>> getAllContacts() {
        return ResponseEntity.ok(contactUsService.getAllContacts());
    }

    @GetMapping("/api/contactus/get/{id}")
    public ResponseEntity<ContactUs> getContactById(@PathVariable int id) {
        return ResponseEntity.ok(contactUsService.getContactById(id));
    }

    @PostMapping("/api/contactus/save")
    public ResponseEntity<ContactUs> createContact(@RequestBody ContactUs contactUs) {
        return ResponseEntity.ok(contactUsService.createContact(contactUs));
    }

    @PutMapping("/api/contactus/update/{id}")
    public ResponseEntity<ContactUs> updateContact(@PathVariable int id, @RequestBody ContactUs contactUs) {
        return ResponseEntity.ok(contactUsService.updateContact(id, contactUs));
    }

    @DeleteMapping("/api/contactus/delete/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        contactUsService.deleteContact(id);
        return ResponseEntity.ok("Contact deleted successfully");
    }
}
