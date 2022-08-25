package com.condigence.service;

import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Profile;
import com.condigence.model.User;
import com.condigence.repository.ProfileRepository;
import com.condigence.repository.UserRepository;

import com.condigence.utils.OTPGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;


    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {
        User user = userDao.findByContact(contact);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with contact: " + contact);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }


    public User findByUserContact(String contact) {
        return userDao.findByContact(contact);
    }

    public UserDTO save(UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }
}