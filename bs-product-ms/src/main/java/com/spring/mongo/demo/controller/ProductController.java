package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bs-products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/say")
	public String sayHello() {
		return "Hello Spring boot";
	}
	
	@GetMapping
	public List<ProductDTO> getAll() {
		return productService.getAll();
	}
	

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable String id ) {
		return productService.getProductById(id);
	}
	
	@GetMapping("/name/{name}")
	public List<Product> getProductByName(@PathVariable String name ) {
		return productService.getProductByName(name);
	}

	// get product by name (equals())
	@GetMapping("/one-by-name/{name}")
	public Product getOneProductByName(@PathVariable String name) {
		return productService.getOneProductByName(name);
	}

	// get product by first name %LIKE%
	@GetMapping("/name-like/{name}")
	public List<Product> getProductByNameLike(@PathVariable String name) {
		return productService.getProductByNameLike(name);
	}
}




