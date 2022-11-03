package com.condigence.controller;



import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.AddressDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Address;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileBean profileBean) {
        logger.info("Updating UserProfile  with id {}", profileBean.getId());
        return new ResponseEntity<UserDTO>(userService.updateUserProfile(profileBean), HttpStatus.OK);
    }

    ////////////////////////////////////////////////Address API's//////////////////////////////////////////////////


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping(value = "/address")
    public ResponseEntity<?> addUserAddress(@RequestBody AddressDTO dto) {
        logger.info("Entering addUserAddress with Details >>>>>>>>  : {}", dto);
        HttpHeaders headers = new HttpHeaders();
        Address address = userService.saveAddress(dto);
        return new ResponseEntity<Address>(address, HttpStatus.CREATED);

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PutMapping(value = "/makeDefault")
    public ResponseEntity<?> makeDefault(@RequestBody AddressDTO dto) {
        logger.info("Updating Address  with id {}", dto.getId());
        List<Address> address = (List<Address>) getAllUserAddresses(dto.getUserId());
        for (Address add : address) {
            System.out.println(add);
            if (add.getId() == dto.getId() && add.getIsDefault().equalsIgnoreCase("N") ) {
                add.setIsDefault("Y");
            } else {
                add.setIsDefault("N");
            }
            userService.updateAddress(add);
        }
        return new ResponseEntity<Address>(HttpStatus.OK);
    }

    @GetMapping("/getDefault/addresses/by/user/{id}")
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


    @GetMapping("/addresses/by/{id}")
    public ResponseEntity<?> getAddressesById(@PathVariable("id") String id) {
        AddressDTO dto = new AddressDTO();

        Optional<Address> address = userService.getAddressesById(id);

        if (address.isPresent()) {
            dto.setId(address.get().getId());
            dto.setLine1(address.get().getLine1());

            dto.setLine2(address.get().getLine2());

            dto.setCity(address.get().getCity());
            dto.setState(address.get().getState());
            dto.setCountry(address.get().getCountry());

            dto.setType(address.get().getType());
            dto.setUserId(address.get().getUserId());
            dto.setIsDefault(address.get().getIsDefault());
        }

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}