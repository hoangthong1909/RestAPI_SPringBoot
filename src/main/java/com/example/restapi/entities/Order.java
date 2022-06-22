package com.example.restapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "orders")
@Component
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "total", precision = 10)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userOrder;

    @OneToMany(mappedBy = "order")
    private List<OrdersDetail> orderdetails;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public User getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(User userOrder) {
        this.userOrder = userOrder;
    }

    public List<OrdersDetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<OrdersDetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}