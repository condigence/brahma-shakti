package com.spring.mongo.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String contact;
	private String address;
	private String username;
	private String password;
	
}
