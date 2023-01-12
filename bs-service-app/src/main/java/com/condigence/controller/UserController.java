package com.condigence.controller;


import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.AddressDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Address;
import com.condigence.service.JwtUserDetailsService;
import com.condigence.service.UserService;
import com.condigence.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/bs-user")
public class UserController {


    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
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
        logger.info("Entering getAll Users");
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

    @PostMapping({"/", "/register"})
    public ResponseEntity<UserDTO> addUser(@RequestBody UserBean userBean) {
        logger.info("Entering addUser with User Details >>>>>>>>  : {}", userBean);
        UserDTO userDTO = null;
        HttpHeaders headers = new HttpHeaders();
        if (userService.isUserExists(userBean.getContact())) {
            return new ResponseEntity(new CustomErrorType("Sorry User already. Exists with the contact , Plz try Login with this contact: " + userBean.getContact()),
                    HttpStatus.CONFLICT);
        } else {
            userDTO = userDetailsService.register(userBean);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        }
    }

    @PostMapping({"/update-info"})
    public ResponseEntity<UserDTO> updateUserProfile(@RequestBody ProfileBean profileBean) {
        logger.info("Entering addUser with User Details >>>>>>>>  : {}", profileBean);
        UserDTO userDTO = null;
        HttpHeaders headers = new HttpHeaders();
        if (userService.isUserExists(profileBean.getContact())) {
            userDTO = userDetailsService.updateUserProfile(profileBean);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity(new CustomErrorType("Sorry User does not exists for this contact you have provided! Please contact Admin : "),
                    HttpStatus.CONFLICT);
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////

//        @SuppressWarnings({"rawtypes", "unchecked"})
//    @PutMapping(value = "/update")
//    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileBean profileBean) {
//        logger.info("Updating UserProfile  with id {}", profileBean.getId());
//        return new ResponseEntity<UserDTO>(userService.updateUserProfile(profileBean), HttpStatus.OK);
//    }

    ////////////////////////////////////////////////Address API's//////////////////////////////////////////////////


    @SuppressWarnings({"unchecked", "rawtypes"})
    @PostMapping(value = "/address")
    public ResponseEntity<?> addUserAddress(@RequestBody AddressDTO dto) {
        logger.info("Entering addUserAddress with Details >>>>>>>>  : {}", dto);
        if (dto.getUserId() != null && userService.isUserIdExists(dto.getUserId())) {
            HttpHeaders headers = new HttpHeaders();
            Address address = userService.saveAddress(dto);
            return new ResponseEntity<Address>(address, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(new CustomErrorType("Sorry User does not exists! Please contact Admin : "),
                    HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/addresses/by/user/{id}")
    public ResponseEntity<?> getAllUserAddressesById(@PathVariable("id") String id) {
        List<AddressDTO> dtos = new ArrayList<>();
        List<Address> addresses = userService.getAllAddressesByUserId(id);
        for (Address address : addresses) {
            AddressDTO dto = new AddressDTO();
            dto.setId(address.getId());
            dto.setLine1(address.getLine1());

            dto.setLine2(address.getLine2());

            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());

            dto.setType(address.getType());
            dto.setUserId(address.getUserId());
            dto.setIsDefault(address.getIsDefault());
            dtos.add(dto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    public List<Address> getAllUserAddresses(String id) {
        List<Address> addresses = userService.getAllAddressesByUserId(id);
        return addresses;

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @PutMapping(value = "/makeDefault")
    public ResponseEntity<?> makeDefault(@RequestBody AddressDTO dto) {
        logger.info("Updating Address  with id {}", dto.getId());
        List<Address> address = getAllUserAddresses(dto.getUserId());
        for (Address add : address) {

            System.out.println(add);
            if (add.getId().equalsIgnoreCase(dto.getId())) {
                add.setIsDefault("Y");

//            add.setType(dto.getType());
//            add.setLine1(dto.getLine1());
//            add.setLine2(dto.getLine2());
//            add.setPin(dto.getPin());
//            add.setCity(dto.getCity());
//            add.setState(dto.getState());
//            add.setCountry(dto.getCountry());
//            add.setUserId(dto.getUserId());


            } else {
                add.setIsDefault("N");
            }


            userService.updateAddress(add);
        }
        return new ResponseEntity<Address>(HttpStatus.OK);
    }

    @GetMapping("/getDefault/address/by/user/{id}")
    public ResponseEntity<?> getDefaultAddressesById(@PathVariable("id") String id) {
        AddressDTO dto = new AddressDTO();
        List<Address> addresses = userService.getAllAddressesByUserId(id);
        for (Address address : addresses) {
            if (address.getIsDefault().equalsIgnoreCase("Y")) {
                dto.setId(address.getId());
                dto.setLine1(address.getLine1());
                dto.setType(address.getType());
                dto.setUserId(address.getUserId());
                dto.setIsDefault(address.getIsDefault());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


//    @GetMapping("/addresses/by/{id}")
//    public ResponseEntity<?> getAddressesById(@PathVariable("id") String id) {
//        AddressDTO dto = new AddressDTO();
//
//        Optional<Address> address = userService.getAddressesById(id);
//
//        if (address.isPresent()) {
//            dto.setId(address.get().getId());
//            dto.setLine1(address.get().getLine1());
//
//            dto.setLine2(address.get().getLine2());
//
//            dto.setCity(address.get().getCity());
//            dto.setState(address.get().getState());
//            dto.setCountry(address.get().getCountry());
//
//            dto.setType(address.get().getType());
//            dto.setUserId(address.get().getUserId());
//            dto.setIsDefault(address.get().getIsDefault());
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(dto);
//    }

    //TODO:FIX me
    @SuppressWarnings({"unchecked", "rawtypes"})
    @DeleteMapping(value = "/address/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Users Address with id {}", id);
        if (userService.getAddressById(id).isPresent()) {
            userService.deleteAddressById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address That your are trying to delete does not exist! Please contact admin!");
        }


    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}