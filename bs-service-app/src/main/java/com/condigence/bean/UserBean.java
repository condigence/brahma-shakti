package com.condigence.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UserBean implements Serializable {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String contact;
	private String address;
	private String username;
	@JsonIgnore
	private String password;
	
}
