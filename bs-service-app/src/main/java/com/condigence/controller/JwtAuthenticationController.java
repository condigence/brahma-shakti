package com.condigence.controller;

import com.condigence.bean.UserBean;
import com.condigence.config.JwtRequest;
import com.condigence.config.JwtResponse;
import com.condigence.config.JwtTokenUtil;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.service.JwtUserDetailsService;
import com.condigence.utils.CustomErrorType;

import io.swagger.annotations.Api;
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
        // Need to Pass contact as Username
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping( {"/", "/login", "otp"} )
    public ResponseEntity<?> generateOTP(@RequestBody UserDTO userDTO) throws Exception {
        logger.info("Entering generateOTP with user contact number >>>>>>>>  : {}", userDTO.getContact());

        String contactNumber= userDTO.getContact();

        if (contactNumber == null || contactNumber == "") {
            logger.info("Please provide contact!");
            return new ResponseEntity(new CustomErrorType("Please provide contact!"), HttpStatus.NOT_FOUND);
        }
        // Check If User exist already
        // Verify Contact
        User userDetails = userDetailsService.findByUserContact(contactNumber);
        if (userDetails != null) {
            logger.info("User is already Registered! OTP Generated!");
            userDTO.setRegistered(true);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        } else {
            logger.info("User not Registered! OTP Generated!");
            userDTO.setRegistered(false);
            return new ResponseEntity(userDTO, HttpStatus.OK);
        }

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(value = "/validate-otp")
    public ResponseEntity<?> validateOTP(@RequestBody UserDTO userDTO) {
        logger.info("Entering validateOTP user Details >>>>>>>>  : {}", userDTO.getContact() , userDTO.getOtp());
        // HttpHeaders headers = new HttpHeaders();
        System.out.println("Inside verifyOTP with contact " + userDTO.getContact());

            if (userDTO.getOtp() != null && userDTO.getOtp().equalsIgnoreCase(getGOTP())) {
                System.out.println("OTP Match");
                User userDetails = userDetailsService.findByUserContact(userDTO.getContact());
                if(!userDTO.isRegistered() && userDetails == null){
                    userDetails = userDetailsService.saveNewUser(userDTO);
                }
                userDTO.setRegistered(true);
                userDTO.setContact(userDetails.getContact());
                userDTO.setFirstName(userDetails.getFirstName());
                userDTO.setEmail(userDetails.getEmail());
                userDTO.setLastName(userDetails.getLastName());
                userDTO.setId(userDetails.getId());
                userDTO.setOtp("****");
                //202 Accepted => OTP validation Successful
                return new ResponseEntity(userDTO, HttpStatus.ACCEPTED);
            } else {
                System.out.println("OTP did not Match");
                //count
                //

               // 401 => OTP is not valid ( Unauthorized ).
                return new ResponseEntity(new CustomErrorType("Sorry, OTP is not valid. Try again!"), HttpStatus.UNAUTHORIZED);
            }
    }

    private String getGOTP() {
        return "1234";
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
