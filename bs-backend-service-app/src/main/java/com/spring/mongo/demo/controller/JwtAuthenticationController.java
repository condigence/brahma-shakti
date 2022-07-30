package com.spring.mongo.demo.controller;

import com.spring.mongo.demo.config.JwtRequest;
import com.spring.mongo.demo.config.JwtResponse;
import com.spring.mongo.demo.config.JwtTokenUtil;

import com.spring.mongo.demo.dto.UserDTO;
import com.spring.mongo.demo.model.User;
import com.spring.mongo.demo.service.JwtUserDetailsService;
import com.spring.mongo.demo.utils.CustomErrorType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api
public class JwtAuthenticationController {

    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ApiOperation(value = "This method is used to get the clients.")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        logger.info("Entering Verify User registration  with user Details >>>>>>>>  : {}", user);
        // Verify username
        User userDetails = userDetailsService.findByUserName(user.getUsername());
        if (userDetails != null) {
            return new ResponseEntity(new CustomErrorType("User Already Registered!"), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(userDetailsService.save(user), HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDTO user) throws Exception {
        logger.info("Entering login with user Details >>>>>>>>  : {}", user);
        // HttpHeaders headers = new HttpHeaders();

        // Check If Username Not Provided
        if (user.getUsername() == null || user.getUsername() == "") {
            return new ResponseEntity(new CustomErrorType("Please provide contact!"), HttpStatus.NOT_FOUND);
        }
        // Verify Contact
        User userDetails = userDetailsService.findByUserName(user.getUsername());
        if (userDetails != null) {
            return new ResponseEntity(userDetails, HttpStatus.OK);
        } else {
            System.out.println("User Not Found!");
            return new ResponseEntity(new CustomErrorType("Contact Not Found! Please Register"), HttpStatus.NOT_FOUND);
        }

    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
