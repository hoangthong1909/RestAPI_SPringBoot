package com.example.restapi.controller;

import com.example.restapi.entities.Category;
import com.example.restapi.entities.User;
import com.example.restapi.repository.ICategoryRepository;
import com.example.restapi.repository.IUserRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private ICategoryRepository repository;

    //GET http://localhost:8080/api/category
    @GetMapping("")
    public List<Category> getAllCategory() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/category/{id}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Category> category = repository.findById(id);
        return category.isPresent()
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query category successfully", category))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find category with id = " + id, ""));
    }
    // POST http://localhost:8080/api/category/insert
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertCategory(@RequestBody Category newCategory) {
        newCategory.setStatus(1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "insert category successfully", repository.save(newCategory)));
    }
    //PUT http://localhost:8080/api/category/{id}
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateCategory(@RequestBody Category newCategory,@PathVariable Integer id){
        Category updateCategory=repository.findById(id)
                .map( category-> {
                    category.setName(newCategory.getName());
                    category.setStatus(1);
                    return repository.save(category);
                }).orElseGet(()->{
                    newCategory.setStatus(1);
                    return repository.save(newCategory);
                });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "update category successfully", updateCategory));
    }
    //DELETE http://localhost:8080/api/category/{id}
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteCategory (@PathVariable Integer id) {
        boolean exists=repository.existsById(id);
        if (exists){
            Category category=repository.findById(id).get();
            category.setStatus(0);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete category successfully",repository.save(category)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find category to delete", ""));
    }
}
