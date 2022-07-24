package com.spring.mongo.demo.service;

import com.spring.mongo.demo.model.User;

import java.util.List;

public interface UserQueryService {

    List<User> getAll();

    List<User> getUserByFirstName(String firstName);

    User getSingleUserByFirstName(String firstName);

    List<User> getUserByFirstNameLike(String firstName);

    User getSingleUserByLastName(String lastName);

    List<User> getUserByEmail(float email);

    List<User> getUserByContact(float contact);

    List<User> getUserByAddress(float address);
}
