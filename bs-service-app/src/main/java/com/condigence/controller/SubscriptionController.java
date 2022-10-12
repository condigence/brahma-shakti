package com.condigence.controller;

import com.condigence.dto.SubscriptionDetailDTO;
import com.condigence.service.SubscriptionService;
import com.condigence.utils.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author Vish
 */
@Slf4j
@RestController
@RequestMapping("/api/bs-subscription")
public class SubscriptionController {

	public static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionService subscriptionService;

	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getAllByUserId(@PathVariable String userId) {
		logger.info("Fetching Subscription with userid {}", userId);
		List<SubscriptionDetailDTO> subscriptions = subscriptionService.getMySubscriptionsByUserId(userId);
		if (subscriptions.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
		}
		 else {
			return new ResponseEntity(new CustomErrorType("subscriptions not found for User Id : "+userId), HttpStatus.NOT_FOUND);
		}
	}

}




