package com.condigence.controller;



import com.condigence.model.User;
import com.condigence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bs-user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/say")
    public String sayHello() {
        return "Hello Spring boot";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/firstName/{firstName}")
    public List<User> getEmployeeByName(@PathVariable String firstName ) {
        return userService.getUserByFirstName(firstName);
    }

    @GetMapping("/one-by-firstName/{firstName}")
    public User getOneEmployeeByFirstName(@PathVariable String firstName) {
        return userService.getSingleUserByFirstName(firstName);
    }

    @GetMapping("/firstName-like/{firstName}")
    public List<User> getEmployeeByFirstNameLike(@PathVariable String firstName) {
        return userService.getUserByFirstNameLike(firstName);
    }

    @GetMapping("/one-by-lastName/{lastName}")
    public User getEmployeeBylastName(@PathVariable String lastName) {
        return userService.getSingleUserByLastName(lastName);
    }

    @GetMapping("/one-by-email/{email}")
    public List<User> getEmployeeByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/one-by-contact/{contact}")
    public List<User> getEmployeeByContact(@PathVariable String contact) {
        return userService.getUserByContact(contact);
    }

    @GetMapping("/one-by-address/{address}")
    public List<User> getEmployeeByAddress(@PathVariable String address) {
        return userService.getUserByAddress(address);
    }
}
