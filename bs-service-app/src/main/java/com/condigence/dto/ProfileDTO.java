package com.condigence.dto;

import com.condigence.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO implements Serializable {

    private String id;
    private String nickName;
    private String fullName;
    private String firstName;
    private String lastName;
    private String dob;
    private String secondaryEmail;
    private String secondaryContact;
    private List<Address> addresses;
    private String userId;
    private ImageDTO image;


}
