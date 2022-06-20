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
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}