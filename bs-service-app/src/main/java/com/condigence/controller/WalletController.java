package com.condigence.controller;


import com.condigence.service.UserService;
import com.condigence.utils.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bs-wallet")
public class WalletController {

	public static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	public void setApp(AppProperties app) {
		this.app = app;
	}

	private AppProperties app;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/balance/{userId}")
	public void addBalance(@PathVariable("userId") String userId, @RequestBody int amount) {
		userService.addBalance(userId, amount);
	}

	@GetMapping("/balance/{userId}")
	public ResponseEntity<?> getBalance(@PathVariable("userId") String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getBalance(userId));
	}
}
