package com.ecommerce.brandlyandco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productname;
    private double  specialprice;
    private double regularprice;
    private int quantity;
    private String model;
    private int rating;
    private String description;
    private  int warranty;
    private String salesservice;
    private String policy;
    private double offer;
    private  String imagea;
    private String imageb;
    private String imagec;
    private String imaged;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagory_id")
    private Category catagory;



}
