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
public class PcForPartAdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;

    private String description;  // Radeon Vega 3 Graphics

    private String performance;  // 3.4GHz

    private String ability;  // PCIe 3.0

    private double regularprice;  // 5500

    private double specialprice;   // 4900

    private int quantity;
    private  String imagea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pcbuilder_id")
    private PcBuilder pcbuilder;
}



