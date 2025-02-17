package com.saverfavor.microbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( nullable = false)
    private  String userid;

    @Column( nullable = false)
    private String password;

    @Column( nullable = false)
    private  String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column( nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private Date dob;

    @Column( nullable = false)
    private String address;

    private  String country;

    private String image;

    private String referralCode;





    @PrePersist
    private void generateUserId() {
        if (this.userid == null || this.userid.isEmpty()) {
            this.userid = generateRandomUserId();
        }
    }

    private String generateRandomUserId() {
        int length = 6;
        String characters = "0123456789";
        Random random = new Random();
        StringBuilder userIdBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            userIdBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return userIdBuilder.toString();
    }



}
