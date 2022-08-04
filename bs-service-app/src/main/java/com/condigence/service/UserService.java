package com.condigence.service;

import com.condigence.bean.UserBean;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.model.Wallet;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();

    List<com.condigence.model.User> getUserByFirstName(String firstName);

    com.condigence.model.User getSingleUserByFirstName(String firstName);

    List<com.condigence.model.User> getUserByFirstNameLike(String firstName);

    com.condigence.model.User getSingleUserByLastName(String lastName);

    List<com.condigence.model.User> getUserByEmail(String email);

    User getUserByContact(String contact);

    List<com.condigence.model.User> getUserByAddress(String address);

    void save(UserBean userbean);

    UserDTO getUserById(String userId);

    void deleteById(String id);

    User getUserByUsername(String userName);


    void addBalance(String userId, int amount);

    Wallet getBalance(String userId);
}