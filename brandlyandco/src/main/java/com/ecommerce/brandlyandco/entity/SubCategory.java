package com.ecommerce.brandlyandco.entity;
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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subcategoryname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagory_id")
    private Category catagory;

}
