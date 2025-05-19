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
public class CCBuilderItemDitels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;  // Radeon Vega 3 Graphics

    private String performance;  // 3.4GHz

    private String ability;  // PCIe 3.0

    private double regularprice;  // 5500

    private String benefits;
    private String moralqualities;
    private String opportunity;

    private double specialprice;   // 4900

    private int quantity;
    private  String imagea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ccBuilder_id")
    private CCBuilder ccBuilder;


    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "item_id",nullable = true)
    private Item item;
}

///@ManyToOne(fetch = FetchType.EAGER, optional = true)
//@JoinColumn(name = "item_id", nullable = true)