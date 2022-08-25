package com.condigence.service;


import com.condigence.bean.ProfileBean;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Wallet;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();

    UserDTO getUserByEmail(String email);

    UserDTO getUserByContact(String contact);

    UserDTO getUserById(String userId);

    void deleteById(String id);

    UserDTO saveUser(UserDTO userDTO);

    // Wallet

    void addBalance(String userId, int amount);

    Wallet getBalance(String userId);


    // Profile Related

    UserDTO updateUserProfile(ProfileBean profileBean);

    ProfileDTO saveProfile(ProfileBean profileBean);

    ProfileDTO getProfileById(String id);

    void deleteProfileById(String id);


}