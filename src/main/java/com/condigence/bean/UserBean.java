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
public class UserBean implements Serializable {

    private String id;
    private String email;
    private String firstName;
    private String contact;
    private String addressId;
    private String imageId;
    private String type;
    private String otp;
    private String userId;


}
