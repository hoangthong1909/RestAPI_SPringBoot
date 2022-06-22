package com.example.restapi.controller;

import com.example.restapi.entities.Product;
import com.example.restapi.repository.IProductRepository;
import com.example.restapi.repository.IUserRepository;
import com.example.restapi.responseobject.ResponseObject;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    //GET http://localhost:8080/api/product
    @GetMapping("")
    public Iterable<Product> getAllProduct() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/product/{id}
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Product> product = repository.findById(id);
        return product.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query product successfully", product)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find product with id = " + id, ""));
    }

    // POST http://localhost:8080/api/product/insert
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        newProduct.setStatus(1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "insert product successfully", repository.save(newProduct)));
    }

    //PUT http://localhost:8080/api/product/{id}
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Integer id) {
        Product updateProduct = repository.findById(id).map(product -> {
            product.setCategoryId(newProduct.getCategoryId());
            product.setPrice(newProduct.getPrice());
            product.setQuantity(newProduct.getQuantity());
            product.setName(newProduct.getName());
            product.setImg(newProduct.getImg());
            product.setStatus(1);
            return repository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            newProduct.setStatus(1);
            return repository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "update product successfully", updateProduct));
    }

    //DELETE http://localhost:8080/api/product/{id}
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Integer id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            Product product = repository.findById(id).get();
            product.setStatus(0);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "delete product successfully", repository.save(product)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Cannot find product to delete", ""));
    }

    @GetMapping("searchByPrice")
    ResponseEntity<ResponseObject> filterByPrice(@RequestParam(name = "start", required = false) BigDecimal start, @RequestParam(name = "end", required = false) BigDecimal end) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Search By Price product successfully", repository.findAllByPriceBetweenAndStatus(start, end,1)));
    }
    @GetMapping("searchByName")
    ResponseEntity<ResponseObject> searchByName(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Search By Name product successfully", repository.findAllByNameAndStatus(name,1)));
    }
    @GetMapping("searchByCategory")
    ResponseEntity<ResponseObject> searchByCategory(@RequestParam(name = "categoryId", required = false) Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Search By Category product successfully", repository.findAllByCategory(id)));
    }

//    @GetMapping(value = "/search")
//    public List<Product> fullTextSearch(@RequestParam(value = "searchKey") String searchKey) {
//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//
//        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
//                .buildQueryBuilder()
//                .forEntity(Product.class)
//                .get();
//        org.apache.lucene.search.Query query = queryBuilder
//                .keyword()
//                .onFields("name", "price","img")
//                .matching(searchKey)
//                .createQuery();
//
//        org.hibernate.search.jpa.FullTextQuery jpaQuery
//                = fullTextEntityManager.createFullTextQuery(query, Product.class);
//
//        return jpaQuery.getResultList();
//
//    }

}
