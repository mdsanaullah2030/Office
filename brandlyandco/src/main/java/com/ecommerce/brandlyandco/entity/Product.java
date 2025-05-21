package com.ecommerce.brandlyandco.entity;
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
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String productname;
    private String size;
    private String color;
    private double  specialprice;
    private double regularprice;
    private int quantity;
    private String stylecode;
    private double offer;
    private String fabric;

}
