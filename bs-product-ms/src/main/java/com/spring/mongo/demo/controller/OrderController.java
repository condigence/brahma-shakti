package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.bean.OrderBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.OrderDTO;
import com.spring.mongo.demo.service.CartService;
import com.spring.mongo.demo.service.OrderService;
import com.spring.mongo.demo.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bs-order")
public class OrderController {

	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getByUserId(@PathVariable String userId) {
		logger.info("Fetching Order with userid {}", userId);
		OrderDTO orderDTO = orderService.getOrderByUserId(userId);
		if (orderDTO !=null && !orderDTO.getId().equalsIgnoreCase("0")) {
			return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
		}
		 else {
			return new ResponseEntity(new CustomErrorType("Order not found for User Id : "+userId), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> placeOrder(@RequestBody OrderBean orderBean) {
		logger.info("Entering placeOrder with Order Details >>>>>>>>  : {}", orderBean);
		HttpHeaders headers = new HttpHeaders();
		orderService.placeOrder(orderBean);
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> cancelOrder(@PathVariable("userId") String userId) {
		logger.info("Fetching & Deleting Order with id {}", userId);
		OrderDTO item = orderService.getOrderByUserId(userId);
		if (item != null) {
			orderService.deleteOrderByUserId(userId);
		} else {
			logger.error("Unable to delete. Item with id {} not found.", userId);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Order with userId " + userId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDTO>(HttpStatus.OK);
	}
	

}




