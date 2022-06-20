package com.example.restapi.controller;

import com.example.restapi.entities.Order;
import com.example.restapi.entities.Product;
import com.example.restapi.repository.IOrderRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private IOrderRepository repository;

    //GET http://localhost:8080/api/order
    @GetMapping("")
    public List<Order> getAllOrder() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Order> order = repository.findById(id);
        return order.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query Order successfully", order)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find order with id = " + id, ""));
    }

    //PUT http://localhost:8080/api/order/confirm/{id}
    @PutMapping("confirm/{id}")
    public ResponseEntity<ResponseObject> confirm(@PathVariable Integer id) {
        Order order = repository.findById(id).get();
        order.setStatus(2);
        return order == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find Order with id =" + id, "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Confirm Order is Successfully", order));
    }

    //    PUT http://localhost:8080/api/order/canceled/{id}
    @PutMapping("canceled/{id}")
    public ResponseEntity<ResponseObject> cancel(@PathVariable Integer id) {
        Order order = repository.findById(id).get();
        order.setStatus(3);
        return order == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find Order with id =" + id, "")) : ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Canceled Order is Successfully", order));
    }
}
