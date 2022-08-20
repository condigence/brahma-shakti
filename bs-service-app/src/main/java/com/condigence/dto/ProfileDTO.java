package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO implements Serializable {

	private String id;
	private String nickName;
	private String fullName;
	private String secondaryEmail;
	private String secondaryContact;
	private AddressDTO address;
	private UserDTO user;
	
}
