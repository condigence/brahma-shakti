package com.condigence.controller;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/brahmashakti")
public class HomeController {


	@GetMapping("/healthCheck")
	public String sayHello() {
		return "Hello, I am doing fine!";
	}


}




