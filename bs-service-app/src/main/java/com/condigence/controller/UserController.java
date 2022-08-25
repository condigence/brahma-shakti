package com.condigence.controller;



import com.condigence.bean.UserBean;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.service.JwtUserDetailsService;
import com.condigence.service.UserService;
import com.condigence.utils.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bs-user")
public class UserController {


    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/one-by-email/{email}")
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/one-by-contact/{contact}")
    public UserDTO getUserByContact(@PathVariable String contact) {
        return userService.getUserByContact(contact);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        logger.info("Entering addProduct with Product Details >>>>>>>>  : {}", userDTO);
        HttpHeaders headers = new HttpHeaders();
        userDetailsService.save(userDTO);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Item with id {}", id);
        UserDTO user = userService.getUserById(id);
        if (user != null && !user.getId().equalsIgnoreCase("0")) {
            userService.deleteById(id);
        } else {
            logger.error("Unable to delete. Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

}