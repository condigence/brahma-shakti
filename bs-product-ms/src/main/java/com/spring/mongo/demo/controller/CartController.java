package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.service.CartService;
import com.spring.mongo.demo.service.ProductService;
import com.spring.mongo.demo.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bs-cart")
public class CartController {

	public static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;

	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable String userId) {
		logger.info("Fetching Cart with userid {}", userId);
		CartDTO cartDTO = cartService.getCartByUserId(userId);
		if (cartDTO !=null && !cartDTO.getId().equalsIgnoreCase("0")) {
			return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
		}
		 else {
			return new ResponseEntity(new CustomErrorType("Cart not found for User Id : "+userId), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> addToCart(@RequestBody CartBean cartBean) {
		logger.info("Entering addBrands with Brand Details >>>>>>>>  : {}", cartBean);
		HttpHeaders headers = new HttpHeaders();
		cartService.addToCart(cartBean);
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> clearCart(@PathVariable("userId") String userId) {
		logger.info("Fetching & Deleting Cart with id {}", userId);
		CartDTO item = cartService.getCartByUserId(userId);
		if (item != null) {
			cartService.deleteCartByUserId(userId);
		} else {
			logger.error("Unable to delete. Item with id {} not found.", userId);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Cart with userId " + userId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CartDTO>(HttpStatus.OK);
	}
	

}




