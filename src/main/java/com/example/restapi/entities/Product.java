package com.example.restapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.*;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ScaledNumberField;

import javax.persistence.*;
import java.math.BigDecimal;

//@Indexed
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //    @FullTextField
//    @Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "name", length = 50)
    private String name;

    @ManyToOne
//    @IndexedEmbedded
    @JoinColumn(name = "category_id")
    private Category categoryId;

//    @ScaledNumberField
//    @Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

//    @Field(termVector = TermVector.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "img", length = 100)
    private String img;

    @Column(name = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}