package com.example.restapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getReduce() {
        return reduce;
    }

    public void setReduce(Integer reduce) {
        this.reduce = reduce;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Order> getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(List<Order> orderDiscount) {
        this.orderDiscount = orderDiscount;
    }
}