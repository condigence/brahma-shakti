package com.condigence.model;

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
public class Profile implements Serializable {

	@Id
	private String id;
	private String nickName;
	private String fullName;
	private String secondaryEmail;
	private String secondaryContact;
	private String addressId;
	private String imageId;
	private String dob;
	
}
