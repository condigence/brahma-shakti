package com.condigence.service;

import com.condigence.bean.ProfileBean;
import com.condigence.bean.UserBean;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Profile;
import com.condigence.model.User;
import com.condigence.repository.ProfileRepository;
import com.condigence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        user.setRegistered(userDTO.isRegistered());
//        Profile profile = new Profile();
//        user.setProfileId(profileRepository.save(profile).getId());
        return userDao.save(user);
    }

    public UserDTO updateUserProfile(ProfileBean profileBean) {
        User user = userService.findByUserContact(profileBean.getContact());
        user.setEmail(profileBean.getEmail());
        user.setRegistered(true);
        user.setActive(true);
        User u = userDao.save(user);

        Profile p = profileRepository.findByUserId(u.getId());
        if (p == null) { // User has no profile
            p = new Profile();
        }
        p.setUserId(user.getId());
        p.setFirstName(profileBean.getFirstName());
        p = profileRepository.save(p);
        UserDTO userDTO = userService.getUserById(u.getId());
        return userDTO;
    }

    public UserDTO generateOTP(String contact) {
        User user = new User();
        user.setContact(contact);

        //TODO :FIXME Create SMS OTP generator
        user.setOtp("****");
        user.setActive(false);
        user.setRegistered(false);
        user = userDao.save(user);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setContact(user.getContact());
        dto.setActive(user.isActive());
        return dto;
    }

    public UserDTO register(UserBean userBean) {
        User user = new User();
        user.setContact(userBean.getContact());
        user.setEmail(userBean.getEmail());
        user.setRegistered(true);
        User u = userDao.save(user);
        Profile p = new Profile();
        p.setFirstName(userBean.getFirstName());
        p.setUserId(u.getId());
        Profile savedProfile = profileRepository.save(p);
        return userService.getUserById(u.getId());
    }

    public UserDTO activateUserAndgetUserDetails(UserBean bean, String journeyName) {
        User user = findByUserContact(bean.getContact());
        if (journeyName.equalsIgnoreCase("register")) {
            user.setRegistered(true);
        }
        UserDTO userDTO = new UserDTO();
        Profile p = profileRepository.findByUserId(user.getId());
        if (p == null) { // User has no profile
            p = new Profile();
            p.setUserId(user.getId());
            p = profileRepository.save(p);
        }
        if (!user.isActive()) { // User is not active
            user = userDao.save(user);
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(p.getId());
        profileDTO.setUserId(p.getUserId());
        profileDTO.setFirstName(p.getFirstName());

        userDTO.setRegistered(user.isRegistered());
        userDTO.setContact(user.getContact());
        userDTO.setActive(true);
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setOtp(user.getOtp());
        userDTO.setProfile(profileDTO);
        userDTO.setProfileUpdated(user.isProfileCompleted());
        return userDTO;
    }


}