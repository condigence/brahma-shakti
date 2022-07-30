package com.condigence.controller;

import com.condigence.bean.StockBean;
import com.condigence.dto.CartDTO;
import com.condigence.dto.StockDTO;
import com.condigence.service.StockService;
import com.condigence.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bs-stock")
public class StockController {

	public static final Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockService stockService;

	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}

	@GetMapping("/productId/{productId}")
	public ResponseEntity<?> getStockByProductId(@PathVariable String productId) {
		logger.info("Fetching Stock with userid {}", productId);
		StockDTO stockDTO = stockService.findStockByProductId(productId);
		if (stockDTO !=null && !stockDTO.getId().equalsIgnoreCase("0")) {
			return ResponseEntity.status(HttpStatus.OK).body(stockDTO);
		}
		 else {
			return new ResponseEntity(new CustomErrorType("Cart not found for User Id : "+productId), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add/productId/{productId}")
	public ResponseEntity<?> addStock(@RequestBody StockBean stockBean, @PathVariable String productId) {
		logger.info("Entering addStock with Details >>>>>>>>  : {}", stockBean);
		HttpHeaders headers = new HttpHeaders();
		StockDTO stockDTO = stockService.addStock(stockBean, productId);
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PostMapping("/update/productId/{productId}")
	public ResponseEntity<?> updateStockOnOrder(@RequestBody Integer orderedQuantity, @PathVariable String productId) {
		logger.info("Entering updateStockOnOrder with Brand Details >>>>>>>>  : {}", orderedQuantity);
		HttpHeaders headers = new HttpHeaders();
		StockDTO stockDTO = stockService.updateStock(orderedQuantity, productId);
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@DeleteMapping(value = "/{productId}")
	public ResponseEntity<?> deleteStock(@PathVariable("productId") String productId) {
		logger.info("Fetching & Deleting Order with id {}", productId);
		StockDTO item = stockService.findStockByProductId(productId);
		if (item != null) {
			stockService.deleteStock(productId);
		} else {
			logger.error("Unable to delete. Item with id {} not found.", productId);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Cart with userId " + productId + " not found."),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CartDTO>(HttpStatus.OK);
	}


}




