package com.itshop.ecommerce.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private  String mission;
    private  String vision;
    private  String achievements;
    private  String brandbusinesspartners;
    private  String description;
}
