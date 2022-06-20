package com.example.restapi.repository;


import com.example.restapi.entities.Category;
import com.example.restapi.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query("select obj from Product obj where obj.status=1")
    List<Product> findAll();
}