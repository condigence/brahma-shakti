package com.spring.mongo.demo.service.impl;


import com.spring.mongo.demo.model.User;

import com.spring.mongo.demo.repository.UserRepository;
import com.spring.mongo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getUserByFirstName(String firstName) {
        List<User> users = new ArrayList<>();
        List<User> list = repository.findAll();
        for (User usr : list) {
            if (usr.getFirstName().equalsIgnoreCase(firstName))
                users.add(usr);
        }
        return users;
    }

    @Override
    public User getSingleUserByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    @Override
    public List<User> getUserByFirstNameLike(String firstName) {
        return repository.findByFirstNameLike(firstName);
    }

    @Override
    public User getSingleUserByLastName(String lastName) {
        List<User> employees = repository.findAll();

        for (User user : employees) {
            if (user.getLastName().equalsIgnoreCase(lastName))
                return user;
        }
        return User.builder().id(String.valueOf(0)).firstName("Not Found").lastName("Please enter valid id").build();
    }

    @Override
    public List<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> getUserByContact(String contact) {
        return repository.findByContact(contact);
    }

    @Override
    public List<User> getUserByAddress(String address) {
        return repository.findByAddress(address);
    }
}
