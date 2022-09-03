package com.condigence.dto;

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
public class AddressDTO implements Serializable {

    private String id;
    private String userId;
    private String type;
    private String line1;
    private String line2;
    private String pin;
    private String city;
    private String state;
    private String country;
    private String contact;
    private String isDefault;

}
