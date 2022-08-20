package com.condigence.service.impl;


import com.condigence.bean.UserBean;
import com.condigence.dto.AddressDTO;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.Address;
import com.condigence.model.Profile;
import com.condigence.model.User;
import com.condigence.model.Wallet;
import com.condigence.repository.AddressRepository;
import com.condigence.repository.ProfileRepository;
import com.condigence.repository.UserRepository;
import com.condigence.repository.WalletRepository;
import com.condigence.service.ProfileService;
import com.condigence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void updateProfile(ProfileDTO profileDTO) {
        Profile profile = repository.findByByUserId(profileDTO.getUser().getId());
        profile.setFullName(profile.getFullName());
        repository.save(profile);
    }

    @Override
    public void save(ProfileDTO profileDTO) {

        Profile profile = new Profile();

        repository.save(profile);

    }

    @Override
    public ProfileDTO getUserById(String userId) {
        Profile profile = repository.findByByUserId(userId);
        ProfileDTO profileDTO  =  new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setFullName(profile.getFullName());
        profileDTO.setNickName(profile.getNickName());
        profileDTO.setSecondaryContact(profile.getSecondaryContact());
        profileDTO.setSecondaryEmail(profile.getSecondaryEmail());

        Address address = addressRepository.findById(profile.getAddressId()).get();
        AddressDTO addressDTO = new AddressDTO();


        profileDTO.setAddress(addressDTO);

        User user = userRepository.findByContact(profile.getUserId());
        UserDTO  userDTO = new UserDTO();
        userDTO.setContact(user.getContact());
        userDTO.setId(user.getId());


        profileDTO.setUser(userDTO);

        return profileDTO;
    }

    @Override
    public void deleteByUserId(String userId) {
        Profile profile = repository.findByByUserId(userId);
        repository.deleteById(profile.getId());
    }
}