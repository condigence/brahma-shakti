package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO implements Serializable {

	private String id;

	private float subtotalAmount;
	private float grandTotal;

	private int totalItemCount;
	private float discountAmount;
	private float taxAmount;

	private String lastUpdated;

	private List<CartDetailDTO> itemDetails;

	private List<SubscriptionDetailDTO> subscriptionDetails;

	private String userId;
	private UserDTO userDTO;

	String outOfStockMessage;
	
}
