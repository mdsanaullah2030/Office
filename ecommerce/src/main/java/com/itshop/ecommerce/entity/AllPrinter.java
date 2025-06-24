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
public class AllPrinter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;


    private String productid;

    @Column( nullable = false)
    private String name;

    private String type;

   private String printspeed;

   private  String printwidth;

   private String printresolution;

   private String interfaces;

   private  String bodycolor;

    private int quantity;

    private double regularprice;

    private double  specialprice;


    private int warranty;

    @Column(columnDefinition = "LONGTEXT")
    private String  title;
    @Column(columnDefinition = "LONGTEXT")
    private  String details;
    @Column(columnDefinition = "LONGTEXT")
    private String  specification;
    private  String imagea;
    private String imageb;
    private String imagec;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagory_id")
    private Catagory catagory;



    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;



    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "brand_id", nullable = true)
    private Brand brand;


    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "productItem_id", nullable = true)
    private ProductItem productItem;


}
