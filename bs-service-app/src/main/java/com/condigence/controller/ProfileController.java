//package com.condigence.controller;
//
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@CrossOrigin()
//@RestController
//@RequestMapping("/api/bs-profile")
//public class ProfileController {
//
//
//    public static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/say")
//    public String sayHello() {
//        return "Hello Spring boot";
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<?>  getUserProfileById(@PathVariable String id) {
//        return new ResponseEntity<ProfileDTO>(userService.getProfileById(id), HttpStatus.OK);
//    }
//
//    @GetMapping("/contact/{contact}")
//    public ResponseEntity<?>  getUserProfileByContact(@PathVariable String contact) {
//        UserDTO userDTO = userService.getUserByContact(contact);
//        if(userDTO.getProfile() != null){
//            return new ResponseEntity<ProfileDTO>(userService.getProfileById(userDTO.getProfile().getId()), HttpStatus.OK);
//        }else {
//            return new ResponseEntity(new CustomErrorType("Profile not found for User contact : "+contact), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping(value = "/")
//    public ResponseEntity<?> createProfile(@RequestBody ProfileBean profileBean) {
//
//        logger.info("Entering createProfile with profileDTO Details >>>>>>>>  : {}", profileBean);
//        HttpHeaders headers = new HttpHeaders();
//        userService.saveProfile(profileBean);
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @PutMapping(value = "/update")
//    public ResponseEntity<?> updateUserProfile(@RequestBody @NotNull ProfileBean profileBean) {
//        logger.info("Updating UserProfile  with id {}", profileBean.getId());
//        return new ResponseEntity<UserDTO>(userService.updateUserProfile(profileBean), HttpStatus.OK);
//    }
//
//    //////////////////// Address /////////
//
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @DeleteMapping(value = "/addresses/{id}")
//    public ResponseEntity<?> deleteAddressById(@PathVariable("id") String id) {
//        logger.info("Fetching & Deleting Users Address with id {}", id);
//        userService.deleteAddressById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    @PostMapping(value = "/address")
//    public ResponseEntity<?> addUserAddress(@RequestBody AddressDTO dto) {
//        logger.info("Entering addUserAddress with Details >>>>>>>>  : {}", dto);
//        HttpHeaders headers = new HttpHeaders();
//        Address address = userService.saveAddress(dto);
//        return new ResponseEntity<Address>(address, HttpStatus.CREATED);
//
//    }
//
//    @GetMapping("/addresses/by/user/{id}")
//    public ResponseEntity<?> getAllUserAddressesById(@PathVariable("id") String id) {
//        List<AddressDTO> dtos = new ArrayList<>();
//        List<Address> addresses = userService.getAllAddressesByUserId(id);
//        for (Address address : addresses) {
//            AddressDTO dto = new AddressDTO();
//            dto.setId(address.getId());
//            dto.setLine1(address.getLine1());
//            dto.setLine2(address.getLine2());
//
//            dto.setCity(address.getCity());
//            dto.setState(address.getState());
//            dto.setCountry(address.getCountry());
//
//            dto.setType(address.getType());
////            dto.setUserId(address.getUserId());
////            dto.setIsDefault(address.getIsDefault());
//            dtos.add(dto);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(dtos);
//    }
//
////    public List<Address> getAllUserAddresses(Long id) {
////        List<Address> addresses = userService.getAllAddressesByUserId(id);
////        return addresses;
////
////    }
//
////    @SuppressWarnings({ "rawtypes", "unchecked" })
////    @PutMapping(value = "/v1/makeDefault")
////    public ResponseEntity<?> makeDefault(@RequestBody AddressDTO dto) {
////        logger.info("Updating Address  with id {}", dto.getId());
////        List<Address> address = (List<Address>) getAllUserAddresses(dto.getUserId());
////        for (Address add : address) {
////            System.out.println(add);
////            if (add.getId() == dto.getId() && add.getIsDefault().equalsIgnoreCase("N") ) {
////                add.setIsDefault("Y");
////            } else {
////                add.setIsDefault("N");
////            }
////            service.updateAddress(add);
////        }
////        return new ResponseEntity<Address>(HttpStatus.OK);
////    }
//
////    @GetMapping("/v1/getDefault/addresses/by/user/{id}")
////    public ResponseEntity<?> getDefaultAddressesById(@PathVariable("id") long id) {
////        AddressDTO dto = new AddressDTO();
////
////        List<Address> addresses = service.getAllAddressesByUserId(id);
////        for (Address address : addresses) {
////            if (address.getIsDefault().equalsIgnoreCase("Y")) {
////                dto.setId(address.getId());
////                dto.setLine1(address.getLine1());
////                dto.setType(address.getType());
////                dto.setUserId(address.getUserId());
////                dto.setIsDefault(address.getIsDefault());
////            }
////        }
////        return ResponseEntity.status(HttpStatus.OK).body(dto);
////    }
//
//
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
//         //   dto.setUserId(address.get().getUserId());
//          //  dto.setIsDefault(address.get().getIsDefault());
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(dto);
//    }
//
//
//}