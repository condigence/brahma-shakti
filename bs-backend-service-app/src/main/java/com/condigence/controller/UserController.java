package com.spring.mongo.demo.controller;



import com.spring.mongo.demo.bean.UserBean;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.dto.UserDTO;
import com.spring.mongo.demo.model.User;
import com.spring.mongo.demo.service.UserService;
import com.spring.mongo.demo.utils.CustomErrorType;
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

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/firstName/{firstName}")
    public List<User> getUserByName(@PathVariable String firstName ) {
        return userService.getUserByFirstName(firstName);
    }

    @GetMapping("/one-by-firstName/{firstName}")
    public User getOneUserByFirstName(@PathVariable String firstName) {
        return userService.getSingleUserByFirstName(firstName);
    }

    @GetMapping("/firstName-like/{firstName}")
    public List<User> getUserByFirstNameLike(@PathVariable String firstName) {
        return userService.getUserByFirstNameLike(firstName);
    }

    @GetMapping("/one-by-lastName/{lastName}")
    public User getUserBylastName(@PathVariable String lastName) {
        return userService.getSingleUserByLastName(lastName);
    }

    @GetMapping("/one-by-email/{email}")
    public List<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/one-by-contact/{contact}")
    public List<User> getUserByContact(@PathVariable String contact) {
        return userService.getUserByContact(contact);
    }

    @GetMapping("/one-by-address/{address}")
    public List<User> getUserByAddress(@PathVariable String address) {
        return userService.getUserByAddress(address);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> addUser(@RequestBody UserBean userBean) {

        logger.info("Entering addProduct with Product Details >>>>>>>>  : {}", userBean);
        HttpHeaders headers = new HttpHeaders();
        userService.save(userBean);
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
