package com.example.restapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @Column(name = "role")
    private Boolean role;

    @Column(name = "status")
    private Integer status;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "email", length = 50)
    private String email;

    @JsonBackReference
    @OneToMany(mappedBy = "userOrder")
    private List<Order> orders;

}