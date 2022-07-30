package com.condigence.service;

import com.condigence.bean.UserBean;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();

    List<User> getUserByFirstName(String firstName);

    User getSingleUserByFirstName(String firstName);

    List<User> getUserByFirstNameLike(String firstName);

    User getSingleUserByLastName(String lastName);

    List<User> getUserByEmail(String email);

    List<User> getUserByContact(String contact);

    List<User> getUserByAddress(String address);

    void save(UserBean userbean);

    UserDTO getUserById(String userId);

    void deleteById(String id);

    User getUserByUsername(String userName);


}
