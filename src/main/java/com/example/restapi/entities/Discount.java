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
@Table(name = "discount")
public class Discount {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "reduce")
    private Integer reduce;

    @Column(name = "status")
    private Integer status;

    @JsonBackReference
    @OneToMany(mappedBy = "discount")
    private List<Order> orderDiscount;

}