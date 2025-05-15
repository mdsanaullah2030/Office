package com.itshop.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceFeature {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emi;
    private String support;
    private String payment;
    private String delivery ;


}
