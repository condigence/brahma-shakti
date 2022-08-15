package com.condigence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String contact;
	private String address;
	private String username;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String otp;
	private String otpCreationTime;
	
}
