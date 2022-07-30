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
	private String userId;
	private int totalItemCount;
	private int totalItems;
	private float discountAmount;
	private float taxAmount;
	private float subtotalAmount;
	private float grandTotal;
	private String lastUpdated;
	private List<CartDetailDTO> itemDetails;
	
}
