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
public class ServiceFeature {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "LONGTEXT")
    private String emi;
    @Column(columnDefinition = "LONGTEXT")
    private String support;
    @Column(columnDefinition = "LONGTEXT")
    private String payment;
    @Column(columnDefinition = "LONGTEXT")
    private String delivery ;


}
