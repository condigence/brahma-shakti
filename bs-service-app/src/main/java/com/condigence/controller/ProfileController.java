package com.condigence.controller;


import com.condigence.dto.ProfileDTO;
import com.condigence.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bs-profile")
public class ProfileController {


    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileService profileService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/my-profile/{userId}")
    public ProfileDTO getUserByAddress(@PathVariable String userId) {
        return profileService.getUserById(userId);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDTO profileDTO) {

        logger.info("Entering updateProfile with profileDTO Details >>>>>>>>  : {}", profileDTO);
        HttpHeaders headers = new HttpHeaders();
        profileService.save(profileDTO);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


}