package com.example.restapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private  Category categoryId;

    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "img", length = 100)
    private String img;

    @Column(name = "status")
    private Integer status;

}