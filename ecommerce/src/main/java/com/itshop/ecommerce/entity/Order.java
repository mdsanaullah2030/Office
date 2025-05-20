package com.itshop.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders") //  Here we renamed the table!
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phoneNo;
    private String productid;
    private String productname;
    private int quantity;
    private Double price;
    private String districts;
    private  String upazila;
    private  String address;



    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "requestdate", updatable = false)
    private Date requestdate;

    @PrePersist
    protected void onCreate() {
        this.requestdate = new Date();
    }


    private  String status;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productDetails_id")
    private ProductDetails productDetails;




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addToCart_id")
    private AddToCart addToCart;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pcForPartAdd_id")
    private PcForPartAdd pcForPartAdd;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ccBuilderItem_id")
    private CCBuilderItemDitels ccBuilderItemDitels;
}
//@ManyToOne(fetch = FetchType.EAGER, optional = true)
//@JoinColumn(name = "item_id", nullable = true)