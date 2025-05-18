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
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ccBuilder_id")
    private CCBuilder ccBuilder;
}
