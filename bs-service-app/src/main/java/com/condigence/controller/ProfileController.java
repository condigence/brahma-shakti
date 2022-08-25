package com.condigence.controller;


import com.condigence.bean.ProfileBean;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.service.ImageService;
import com.condigence.service.JwtUserDetailsService;
import com.condigence.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("/api/bs-profile")
public class ProfileController {


    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/my-profile/{id}")
    public ResponseEntity<?>  getUserProfileById(@PathVariable String id) {
        return new ResponseEntity<ProfileDTO>(userService.getProfileById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createProfile(@RequestBody ProfileBean profileBean) {

        logger.info("Entering createProfile with profileDTO Details >>>>>>>>  : {}", profileBean);
        HttpHeaders headers = new HttpHeaders();
        userService.saveProfile(profileBean);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody @NotNull ProfileBean profileBean) {
        logger.info("Updating UserProfile  with id {}", profileBean.getId());
        return new ResponseEntity<UserDTO>(userService.updateUserProfile(profileBean), HttpStatus.OK);
    }


}