package com.condigence.service;

import com.condigence.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> getUserByFirstName(String firstName);

    User getSingleUserByFirstName(String firstName);

    List<User> getUserByFirstNameLike(String firstName);

    User getSingleUserByLastName(String lastName);

    List<User> getUserByEmail(String email);

    List<User> getUserByContact(String contact);

    List<User> getUserByAddress(String address);

    void save(User user);

    User getUserByUsername(String userName);


}
