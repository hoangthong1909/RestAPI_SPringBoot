package com.example.restapi.controller;

import com.example.restapi.entities.Discount;
import com.example.restapi.repository.IDiscountRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {
    @Autowired
    private IDiscountRepository repository;

    //GET http://localhost:8080/api/discount
    @GetMapping("")
    public List<Discount> getAllDiscount() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/discount/{id}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Discount> discount = repository.findById(id);
        return discount.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query discount successfully", discount))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find discount with id = " + id, ""));
    }
    // POST http://localhost:8080/api/discount/insert
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertDiscount(@RequestBody Discount newDiscount) {
        newDiscount.setStatus(1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "insert discount successfully", repository.save(newDiscount)));
    }
    //PUT http://localhost:8080/api/discount/{id}
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateDiscount(@RequestBody Discount newDiscount,@PathVariable Integer id){
        Discount updateDiscount =repository.findById(id)
                .map( discount-> {
                    discount.setCode(newDiscount.getCode());
                    discount.setReduce(newDiscount.getReduce());
                    discount.setStatus(1);
                    return repository.save(discount);
                }).orElseGet(()->{
                    newDiscount.setId(id);
                    newDiscount.setStatus(1);
                    return repository.save(newDiscount);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "update discount successfully", updateDiscount));
    }
    //DELETE http://localhost:8080/api/discount/{id}
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteDiscount(@PathVariable Integer id) {
        boolean exists=repository.existsById(id);
        if (exists){
            Discount discount=repository.findById(id).get();
            discount.setStatus(0);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete discount successfully",repository.save(discount)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find discount to delete", ""));
    }
}
