package com.condigence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private String id;
    private String email;
    private String contact;
    private String username;
    private ProfileDTO profile;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String otp;

    private boolean isRegistered;
    private boolean isActive;
    private boolean isProfileUpdated;

}
