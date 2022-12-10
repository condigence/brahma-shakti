package com.condigence.service;


import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.AddressDTO;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Address;
import com.condigence.model.User;
import com.condigence.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAll();

    UserDTO getUserByEmail(String email);

    UserDTO getUserByContact(String contact);

    User findByUserContact(String contact);

    UserDTO getUserById(String userId);

    void deleteById(String id);

    UserDTO saveUser(UserBean user);

    boolean isUserExists(String contact);

    // Wallet

    void addBalance(String userId, int amount);

    Wallet getBalance(String userId);


    // Profile Related

    UserDTO updateUserProfile(ProfileBean profileBean);

    ProfileDTO saveProfile(ProfileBean profileBean);

    ProfileDTO getProfileById(String id);

    void deleteProfileById(String id);

    // Address
    Address saveAddress(AddressDTO dto);
    void deleteAddressById(String id);

    Address updateAddress(Address address);

    Optional<Address> getAddressById(String id);

    Optional<Address> getAddressByUserId(String id);

    List<Address> getAllAddresses();

    List<Address> getAllAddressesByUserId(String id);

//    ProfileDTO getProfileByContact(String contact);
}