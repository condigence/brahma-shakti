package com.spring.mongo.demo.service;

import com.spring.mongo.demo.bean.UserBean;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.dto.UserDTO;
import com.spring.mongo.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;

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
