package com.condigence.service;

import com.condigence.dto.ProfileDTO;

public interface ProfileService {

    void updateProfile(ProfileDTO profileDTO);

    void save(ProfileDTO profileDTO);

    ProfileDTO getUserById(String userId);

    void deleteByUserId(String id);
}