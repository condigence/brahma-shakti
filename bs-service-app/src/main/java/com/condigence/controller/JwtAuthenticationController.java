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

    @RequestMapping(value = "/get-otp", method = RequestMethod.POST)
    public ResponseEntity<?> getOTP(@RequestBody String contactNumber) throws Exception {
        logger.info("Entering getOTP with user contact number >>>>>>>>  : {}", contactNumber);

        // Check If User exist already
        if (contactNumber == null || contactNumber == "") {
            logger.info("Please provide contact!");
            return new ResponseEntity(new CustomErrorType("Please provide contact!"), HttpStatus.NOT_FOUND);
        }
        // Verify Contact
        User userDetails = userDetailsService.findByUserContact(contactNumber);
        if (userDetails != null) {
            logger.info("User is already Registered! OTP Generated!");
            UserDTO userDTO = new UserDTO();
            userDTO.setRegistered(true);
            userDTO.setContact(userDetails.getContact());
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } else {
            logger.info("User not Registered! OTP Generated!");
            UserDTO userDTO = new UserDTO();
            userDTO.setContact(contactNumber);
            userDTO.setRegistered(false);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(value = "/validate-otp")
    public ResponseEntity<?> validateOTP(@RequestBody String userContact, String otp) {
        logger.info("Entering validateOTP user Details >>>>>>>>  : {}", userContact , otp);
        // HttpHeaders headers = new HttpHeaders();
        System.out.println("Inside verifyOTP with contact " + userContact);

        User userDetails = userDetailsService.findByUserContact(userContact);

        if (userDetails != null) {
            System.out.println("User present");
            if (userDetails.getOtp().equalsIgnoreCase(otp)) {
                System.out.println("OTP Match");
                UserDTO userDTO = new UserDTO();
                userDTO.setRegistered(true);
                userDTO.setContact(userDetails.getContact());
                userDTO.setAddress(userDetails.getAddress());
                userDTO.setFirstName(userDetails.getFirstName());
                userDTO.setEmail(userDetails.getEmail());
                userDTO.setLastName(userDetails.getLastName());
                userDTO.setId(userDetails.getId());
                return new ResponseEntity(userDTO, HttpStatus.OK);
            } else {
                System.out.println("OTP did not Match");
                return new ResponseEntity(new CustomErrorType("Sorry, Invalid OTP. Try again!"), HttpStatus.NOT_FOUND);
            }

        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setContact(userContact);
            userDTO.setRegistered(false);
            return new ResponseEntity(userDTO, HttpStatus.OK);
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
