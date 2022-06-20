package com.example.restapi.repository;

import com.example.restapi.entities.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdersDetailRepository extends JpaRepository<OrdersDetail, Integer> {
}