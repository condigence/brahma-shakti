package com.condigence.controller;

import com.condigence.dto.ProductDTO;
import com.condigence.model.Product;
import com.condigence.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-query")
public class ProductQueryController {

    @Autowired
    private ProductQueryService productQueryService;

    @GetMapping
    public List<ProductDTO> getAll() {
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
