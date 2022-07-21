package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.service.CartService;
import com.spring.mongo.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bs-cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}

	@GetMapping("/{userId}")
	public CartDTO getCartByUserId(@PathVariable String userId) {
		return cartService.getCartByUserId(userId);
	}
	

}




