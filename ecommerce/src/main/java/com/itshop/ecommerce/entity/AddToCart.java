package com.itshop.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddToCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalprice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productDetails_id")
    private ProductDetails productDetails;

    private int quantity;
    private double price;


    @ManyToOne
    @JoinColumn(name = "pcforpartadd_id")
    private PcForPartAdd pcForPartAdd;


    @ManyToOne
    @JoinColumn(name = "CCItem_id")
    private CCBuilderItemDitels ccBuilderItemDitels;

    @ManyToOne
    @JoinColumn(name = "allLaptop_id")
    private AllLaptop allLaptop;


    @ManyToOne
    @JoinColumn(name = "allNetwork_id")
    private AllNetwork allNetwork;



    @ManyToOne
    @JoinColumn(name = "allPrinter_id")
    private AllPrinter allPrinter;


    @ManyToOne
    @JoinColumn(name = "allCamera_id")
    private AllCamera allCamera;


    @ManyToOne
    @JoinColumn(name = "desktopPcAll_id")
    private DesktopPcAll desktopPcAll;
}


