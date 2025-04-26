package com.itshop.ecommerce.entity;

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
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private int productid;
    private String  name;
    private String categorys;
    private int quantity;
    private double regularprice;
    private double  specialprice;
     private  int tax;
     private String  title;
     private  String details;
     private String  specification;
     private  String imagea;
     private String imageb;
    private String imagec;
    private String imaged;
    private String imagef;




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagory_id")
    private Catagory catagory;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
}
