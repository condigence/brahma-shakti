package com.condigence.dto;

import com.condigence.model.Address;
import com.condigence.model.User;
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
public class ProfileDTO implements Serializable {

	private String id;
	private String nickName;
	private String fullName;
	private String secondaryEmail;
	private String secondaryContact;
	private AddressDTO address;
	private UserDTO user;
	
}
