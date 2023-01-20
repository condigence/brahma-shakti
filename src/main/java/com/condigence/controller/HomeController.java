package com.condigence.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/brahmashakti")
public class HomeController {


    @GetMapping("/healthCheck")
    public String sayHello() {
        return "Hello, I am doing fine!";
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


}




