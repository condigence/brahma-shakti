package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.model.Employee;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.service.EmployeeQueryService;
import com.spring.mongo.demo.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-query")
public class ProductQueryController {

    @Autowired
    private ProductQueryService productQueryService;

    @GetMapping
    public List<Product> getAll() {
        return productQueryService.getAll();
    }


    // getAll employee by first name (equals())
    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name) {
        return productQueryService.getProductByName(name);
    }


    // getAll employee by first name (equals())
    @GetMapping("/one-by-name/{name}")
    public Product getOneProductByName(@PathVariable String name) {
        return productQueryService.getOneProductByName(name);
    }

    // getAll employee by first name %LIKE%
    @GetMapping("/name-like/{name}")
    public List<Product> getProductByNameLike(@PathVariable String name) {
        return productQueryService.getProductByNameLike(name);
    }


}
