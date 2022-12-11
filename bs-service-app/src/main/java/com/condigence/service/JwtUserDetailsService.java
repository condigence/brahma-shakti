package com.condigence.service;

import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Address;
import com.condigence.model.Profile;
import com.condigence.model.User;
import com.condigence.repository.ProfileRepository;
import com.condigence.repository.UserRepository;
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
    private ProfileRepository profileRepository;


    @Autowired
    private UserService userService;


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

    public UserDTO save(UserBean userBean) {
        return userService.saveUser(userBean);
    }

    public User saveNewUser(UserDTO userDTO) {
        User user = new User();
        user.setContact(userDTO.getContact());
        user.setOtp("1234");
        user.setRegistered(userDTO.isRegistered());
        Profile profile = new Profile();
        user.setProfileId(profileRepository.save(profile).getId());
        return userDao.save(user);
    }

    public UserDTO updateUser(UserBean userBean) {
        User user = userService.findByUserContact(userBean.getContact());
        user.setContact(userBean.getContact());
        user.setEmail(userBean.getEmail());
        user.setOtp("1234");
        user.setFirstName(userBean.getFirstName());
        User u = userDao.save(user);

        Profile p= new Profile();
        p.setFullName(user.getFirstName()+" "+user.getLastName());
        if(userService.getAddressByUserId(u.getId()).isPresent()){
            Address address = userService.getAddressByUserId(u.getId()).get();
            if(address != null){
                p.setAddressId(address.getId());
            }
        }
        Profile savedProfile = profileRepository.save(p);


//        ProfileDTO profile = userService.getProfileById(savedProfile.getId());
//        profile.setId(savedProfile.getId());
//        profile.setAddress();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setRegistered(true);
//        userDTO.setContact(user.getContact());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setFirstName(user.getFirstName()); // 05-12-2022 Virneder requested fixed
 //         userDTO.setProfile(profile);
        UserDTO userDTO = userService.getUserById(u.getId());
        return userDTO;
    }
}