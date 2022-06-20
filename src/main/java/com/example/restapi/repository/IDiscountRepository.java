package com.example.restapi.repository;

import com.example.restapi.entities.Category;
import com.example.restapi.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiscountRepository extends JpaRepository<Discount, Integer> {
    @Query("select obj from Discount obj where obj.status=1")
    List<Discount> findAll();
}