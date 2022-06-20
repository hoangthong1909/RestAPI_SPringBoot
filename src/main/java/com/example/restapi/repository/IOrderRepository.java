package com.example.restapi.repository;

import com.example.restapi.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
//    @Query("Select obj from Order obj where obj.userOrder.id = ?1")
//    Page<Order> findAllOrderByUserId(Integer id);

    @Query("Select obj from Order obj where obj.status > 0")
    List<Order> findAll(Integer id);

}