package com.condigence.controller;



import com.condigence.bean.ProfileBean;
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

import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
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

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> addUser(@RequestBody UserBean userBean) {
        logger.info("Entering addUser with User Details >>>>>>>>  : {}", userBean);
        HttpHeaders headers = new HttpHeaders();
        if(userService.isUserExists(userBean.getContact())){
            return new ResponseEntity(new CustomErrorType("Sorry User already. Exists with the contact : "+userBean.getContact()),
                    HttpStatus.CONFLICT);
        }else{
            userDetailsService.save(userBean);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Item with id {}", id);
        if (id != null && !id.equalsIgnoreCase("0")) {
            userService.deleteById(id);
        } else {
            logger.error("Unable to delete. with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

        @SuppressWarnings({"rawtypes", "unchecked"})
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody @NotNull ProfileBean profileBean) {
        logger.info("Updating UserProfile  with id {}", profileBean.getId());
        return new ResponseEntity<UserDTO>(userService.updateUserProfile(profileBean), HttpStatus.OK);
    }

}