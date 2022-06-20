package com.example.restapi.repository;


import com.example.restapi.entities.Category;
import com.example.restapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("select obj from User obj where obj.status=1")
    List<User> findAll();
}