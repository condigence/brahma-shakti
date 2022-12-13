package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO implements Serializable {

	private String id;
	private String number;
	private String type;
	private String dateTime;
	private int totalItemCount;
	private float discountAmount;
	private float taxAmount;
	private float gst;
	private float serviceCharge;
	private float subtotalAmount;
	private float grandTotal;
	private String lastUpdated;
	private UserDTO user;
//	private List<CartDetailDTO> itemDetails;
//	private List<SubscriptionDetailDTO> subscriptionDetails;
	private CartDTO cartDTO;
	private String status; // PENDING or CONFIRMED or CANCELLED
	private String paymentMethod;
	private String razorpayPaymentId;
	private String razorpayOrderId;
	private String razorpaySignature;








	
}
