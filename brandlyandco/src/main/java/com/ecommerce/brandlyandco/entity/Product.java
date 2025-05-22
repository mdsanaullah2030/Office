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
    private String size;
    private String color;
    private double  specialprice;
    private double regularprice;
    private int quantity;
    private String stylecode;
    private double offer;
    private String fabric;
    private String stock;
    private  String imagea;
    private String imageb;
    private String imagec;
    private String imaged;




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagory_id")
    private Category catagory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subCategory_id")
    private SubCategory subCategory;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;


}
