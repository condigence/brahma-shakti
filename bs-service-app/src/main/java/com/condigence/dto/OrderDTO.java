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
public class OrderDTO implements Serializable {

	private String id;
	private String number;
	private String userId;
	private String type;
	private String dateTime;
	private String status;
	private int totalItemCount;
	private float discountAmount;
	private float taxAmount;
	private float gst;
	private float serviceCharge;
	private float subtotalAmount;
	private float grandTotal;
	private String lastUpdated;
	//private List<OrderDetailDTO> orderItems;
	private CartDTO cartDTO;
	
}
