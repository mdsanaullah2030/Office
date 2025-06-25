package com.ecommerce.brandlyandco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String fullName;


    private String email;


    private String phoneNumber;


    private String address;

    private String gender;

    private Date dateOfBirth;

    private String nationalId;
}
