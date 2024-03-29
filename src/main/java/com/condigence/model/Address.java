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
public class Address implements Serializable {

    @Id
    private String id;
    private String type;
    private String line1;
    private String line2;
    private String pin;
    private String city;
    private String state;
    private String country;
    private String contact;
    private String isDefault;
    private String userId;
    private String profileId;

}
