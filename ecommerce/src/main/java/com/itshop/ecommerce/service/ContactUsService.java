package com.itshop.ecommerce.service;

import com.itshop.ecommerce.entity.ContactUs;
import com.itshop.ecommerce.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public List<ContactUs> getAllContacts() {
        return contactUsRepository.findAll();
    }

    public ContactUs getContactById(int id) {
        return contactUsRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public ContactUs createContact(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    public ContactUs updateContact(int id, ContactUs updatedContact) {
        ContactUs existing = getContactById(id);
        existing.setAddress(updatedContact.getAddress());
        existing.setPhonenumbers(updatedContact.getPhonenumbers());
        existing.setEmail(updatedContact.getEmail());
        existing.setSaturday(updatedContact.getSaturday());
        return contactUsRepository.save(existing);
    }

    public void deleteContact(int id) {
        contactUsRepository.deleteById(id);
    }
}
