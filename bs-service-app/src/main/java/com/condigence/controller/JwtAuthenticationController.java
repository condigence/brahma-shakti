package com.condigence.controller;

import com.condigence.config.JwtRequest;
import com.condigence.config.JwtResponse;
import com.condigence.config.JwtTokenUtil;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.service.JwtUserDetailsService;
import com.condigence.utils.CustomErrorType;

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

import java.util.Optional;

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

        // Check If User exist with Contact
        if (user.getContact()== null || user.getContact() == "") {
            return new ResponseEntity(new CustomErrorType("Please provide contact!"), HttpStatus.NOT_FOUND);
        }
        // Verify Contact
        User userDetails = userDetailsService.findByUserContact(user.getContact());
        if (userDetails != null) {
            return new ResponseEntity(userDetails, HttpStatus.OK);
        } else {
            System.out.println("User Not Found!");
            return new ResponseEntity(new CustomErrorType("Contact Not Found! Please Register"), HttpStatus.NOT_FOUND);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(value = "/v1/verify/otp")
    public ResponseEntity<?> verifyOTP(@RequestBody UserDTO dto) {
        logger.info("Entering otp with user Details >>>>>>>>  : {}", dto);
        // HttpHeaders headers = new HttpHeaders();
        System.out.println("Inside verifyOTP with contact " + dto.getContact());

        User userDetails = userDetailsService.findByUserContact(dto.getContact());
        if (userDetails != null) {
            System.out.println("User present");
            if (userDetails.getOtp().equalsIgnoreCase(dto.getOtp())) {
                System.out.println("OTP Match");
                dto.setFirstName(userDetails.getFirstName());
                dto.setContact(userDetails.getContact());
                if(!userDetails.getEmail().equalsIgnoreCase("")) {
                    dto.setEmail(userDetails.getEmail());
                }
                dto.setId(userDetails.getId());
                dto.setOtp("");

                return new ResponseEntity(dto, HttpStatus.OK);
            } else {
                System.out.println("OTP did not Match");
                return new ResponseEntity(new CustomErrorType("Sorry, Invalid OTP. Try again!"), HttpStatus.NOT_FOUND);
            }

        } else {
            System.out.println("User Not present");
            return new ResponseEntity(new CustomErrorType("Sorry, Contact Admin!"), HttpStatus.INTERNAL_SERVER_ERROR);
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
