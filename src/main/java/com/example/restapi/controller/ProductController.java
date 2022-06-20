package com.example.restapi.controller;

import com.example.restapi.entities.Product;
import com.example.restapi.entities.User;
import com.example.restapi.repository.IProductRepository;
import com.example.restapi.repository.IUserRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductRepository repository;

    //GET http://localhost:8080/api/product
    @GetMapping("")
    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/product/{id}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Product> product = repository.findById(id);
        return product.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query product successfully", product))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find product with id = " + id, ""));
    }
    // POST http://localhost:8080/api/product/insert
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        newProduct.setStatus(1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "insert product successfully", repository.save(newProduct)));
    }
    //PUT http://localhost:8080/api/product/{id}
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct,@PathVariable Integer id){
        Product updateProduct =repository.findById(id)
                .map( product-> {
                    product.setCategoryId(newProduct.getCategoryId());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());
                    product.setName(newProduct.getName());
                    product.setImg(newProduct.getImg());
                    product.setStatus(1);
                    return repository.save(product);
                }).orElseGet(()->{
                    newProduct.setId(id);
                    newProduct.setStatus(1);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "update user successfully", updateProduct));
    }
    //DELETE http://localhost:8080/api/product/{id}
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Integer id) {
        boolean exists=repository.existsById(id);
        if (exists){
            Product product=repository.findById(id).get();
            product.setStatus(0);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete user successfully",repository.save(product)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find user to delete", ""));
    }
}
