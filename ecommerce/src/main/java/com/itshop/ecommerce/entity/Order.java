package com.itshop.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "addToCart_id")
    private AddToCart addToCart;





    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "productDetails_id")
    private List<ProductDetails> productDetailsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pcForPartAdd_id")
    private List<PcForPartAdd> pcForPartAddList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ccBuilderItem_id")
    private List<CCBuilderItemDitels> ccBuilderItemDitelsList = new ArrayList<>();



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "allLaptop_id")
    private List<AllLaptop> allLaptopList = new ArrayList<>();



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "allPrinter_id")
    private List<AllPrinter> allPrinterList = new ArrayList<>();



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "allCamera_id")
    private List<AllCamera> allCameraList = new ArrayList<>();




    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "allNetwork_id")
    private List<AllNetwork> allNetworkList = new ArrayList<>();

}
