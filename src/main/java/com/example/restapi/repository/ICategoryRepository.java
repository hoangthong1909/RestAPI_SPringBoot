package com.example.restapi.repository;

import com.example.restapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select obj from Category obj where obj.status=1")
    List<Category> findAll();
}