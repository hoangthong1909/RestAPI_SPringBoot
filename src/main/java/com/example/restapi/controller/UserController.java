package com.example.restapi.controller;

import com.example.restapi.entities.User;
import com.example.restapi.repository.IUserRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserRepository repository;

    //GET http://localhost:8080/api/user
    @GetMapping("")
    public List<User> getAllUser() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/user/{id}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<User> user = repository.findById(id);
        return user.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query user successfully", user))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find user with id = " + id, ""));
    }
    // POST http://localhost:8080/api/user/insert
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody User newUser) {
        newUser.setStatus(1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "insert user successfully", repository.save(newUser)));
    }
    //PUT http://localhost:8080/api/user/{id}
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser,@PathVariable Integer id){
                User updateUser =repository.findById(id)
                .map( user-> {
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    user.setAddress(newUser.getAddress());
                    user.setPassword(newUser.getUsername());
                    user.setStatus(1);
                    user.setRole(newUser.getRole());
                    return repository.save(user);
                }).orElseGet(()->{
                    newUser.setStatus(1);
                    return repository.save(newUser);
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "update user successfully", updateUser));
    }
    //DELETE http://localhost:8080/api/user/{id}
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Integer id) {
        boolean exists=repository.existsById(id);
        if (exists){
            User user=repository.findById(id).get();
            user.setStatus(0);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete user successfully",repository.save(user)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find user to delete", ""));
    }
}
