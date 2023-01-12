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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping({"/", "/login", "otp"})
    public ResponseEntity<?> generateOTP(@RequestBody UserDTO userDTO) throws Exception {
        logger.info("Entering generateOTP with user contact number >>>>>>>>  : {}", userDTO.getContact());

        String contactNumber = userDTO.getContact();

        if (contactNumber == null || contactNumber == "") {
            logger.info("Please provide contact!");
            return new ResponseEntity(new CustomErrorType("Please provide contact!"), HttpStatus.NOT_FOUND);
        }
        String userStatus = "";  // NEW ACTIVE REGISTERED
        // Check If User exist already
        // Verify Contact
        User userDetails = userDetailsService.findByUserContact(contactNumber);
        if (userDetails != null) {
            userDTO.setId(userDetails.getId());
            logger.info("User already Exists :) ");
            if (userDetails.isActive()) {
                logger.info("User already Exists :) You have not verified your OTP. Please verify your OTP! ");
                userDTO.setActive(true);
            }
            if (userDetails.isRegistered()) {
                logger.info("User already Exists :) OTP Generated and Sent Plz check your Messages! ");
                userDTO.setRegistered(true);
            }

        } else {
            logger.info("New User :) OTP Generated and Sent Plz check your Messages!");
            userDTO = userDetailsService.generateOTP(contactNumber);
        }
        // Generate OTP save to DB inside User and send it to User
        //TODO: FIX ME
        // userDTO= userDetailsService.generateOTP(contactNumber);

        return new ResponseEntity(userDTO, HttpStatus.OK);

    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @PostMapping(value = "/validate-otp") // login or register
    public ResponseEntity<?> validateOTP(@RequestBody UserBean bean, @RequestParam(defaultValue = "login", required = false) String journeyName) {
        logger.info("Entering validateOTP user Details >>>>>>>>  : {}", bean.getContact(), bean.getOtp());
        // HttpHeaders headers = new HttpHeaders();
        System.out.println("Inside verifyOTP with contact " + bean.getContact());

        if (userDetailsService.findByUserContact(bean.getContact()) == null) {
            return new ResponseEntity(new CustomErrorType("Sorry User does not Exists with this contact , Plz try  again: " + bean.getContact()),
                    HttpStatus.CONFLICT);
        }


        if (bean.getOtp() != null && bean.getOtp().equalsIgnoreCase("1234")) {
            System.out.println("OTP Match");
            UserDTO userDTO = userDetailsService.activateUserAndgetUserDetails(bean, journeyName);
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
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
