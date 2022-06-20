package com.example.restapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "status", nullable = false)
    private Integer status;

    @JsonBackReference
    @OneToMany(mappedBy = "categoryId")
    private List<Product> productList;

}