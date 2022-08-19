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
public class SubscriptionDTO implements Serializable {

	private String id;
	private String fromDate;
	private String toDate;
	private Integer noOfDays;
	private String frequency;
	private UserDTO userDTO;
	private ProductDTO productDTO;
	private String quantity;
}
