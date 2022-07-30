package com.condigence.service.impl;


import com.condigence.bean.UserBean;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.repository.UserRepository;
import com.condigence.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = repository.findAll();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setContact(user.getContact());
            userDTO.setAddress(user.getAddress());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTOS.add(userDTO);
        }


        return userDTOS;
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

    /**
     */
    @Override
    public void save(UserBean userBean) {

        User user = new User();
        user.setId(userBean.getId());
        user.setFirstName(userBean.getFirstName());
        user.setLastName(userBean.getLastName());
        user.setEmail(userBean.getEmail());
        user.setContact(userBean.getContact());
        user.setAddress(userBean.getAddress());
        user.setPassword(userBean.getPassword());
        repository.save(user);
    }

    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> userData = repository.findById(userId);
        UserDTO userDTO = new UserDTO();
        if (userData.isPresent()) {
            User user = userData.get();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setContact(user.getContact());
            userDTO.setAddress(user.getAddress());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            return userDTO;
        } else {
            return userDTO.builder().id("0").build();
        }
    }

   @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    /**
     * @param userName
     * @return
     */
    @Override
    public User getUserByUsername(String userName) {
        return null;
    }


}
