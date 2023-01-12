package com.condigence.model;

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
public class Profile implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nickName;
    private String fullName;
    private String secondaryEmail;
    private String secondaryContact;
    private String userId;
    private String imageId;
    private String primaryAddressId;
    private String dob;

}
