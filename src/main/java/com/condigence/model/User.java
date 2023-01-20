package com.condigence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    private String id;
    private String email;
    private String contact;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String otp;
    private String otpCreationTime;

    private boolean isRegistered; // When Registering by providing email, name and contact etc
    private boolean isActive;  // when verified by OTP
    private boolean hasAddress;  // when user has at least one Primary Address
    private boolean isProfileCompleted;  // when user has updated his profile

}
