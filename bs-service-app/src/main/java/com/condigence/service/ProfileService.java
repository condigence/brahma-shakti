package com.condigence.service;

import com.condigence.bean.UserBean;
import com.condigence.dto.ProfileDTO;
import com.condigence.dto.UserDTO;
import com.condigence.model.User;
import com.condigence.model.Wallet;

import java.util.List;

public interface ProfileService {

    void updateProfile(ProfileDTO profileDTO);

    void save(ProfileDTO profileDTO);

    ProfileDTO getUserById(String userId);

    void deleteByUserId(String id);
}