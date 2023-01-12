package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileBean implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String fullName;
    private String email;
    private String contact;
    private String addressId;
    private String imageId;
    private String type;
    private String otp;
    private String dob;
    private String secondaryEmail;
    private String secondaryContact;
    private String userId;

}
