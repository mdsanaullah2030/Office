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

}


