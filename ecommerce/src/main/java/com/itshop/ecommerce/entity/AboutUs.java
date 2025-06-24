package com.itshop.ecommerce.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AboutUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "LONGTEXT")
    private  String mission;
    @Column(columnDefinition = "LONGTEXT")
    private  String vision;
    @Column(columnDefinition = "LONGTEXT")
    private  String achievements;
    @Column(columnDefinition = "LONGTEXT")
    private  String brandbusinesspartners;
    @Column(columnDefinition = "LONGTEXT")
    private  String description;
}
