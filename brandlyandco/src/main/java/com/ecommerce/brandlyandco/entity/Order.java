package com.ecommerce.brandlyandco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // Specify a non-reserved table name
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phonenumber;
    private String email;
    private String mfs;
    private String name;
    private String productid;
    private String productname;
    private String accountnumber;
    private int quantity;
    private Double price;
    private String districts;
    private String upazila;
    private String address;



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
    @JoinColumn(name = "addToCart_id")
    private AddToCart addToCart;




    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<Product> productList = new ArrayList<>();
}