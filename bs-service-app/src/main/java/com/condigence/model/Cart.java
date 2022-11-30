package com.condigence.model;

import com.condigence.dto.CartDetailDTO;
import com.condigence.dto.SubscriptionDetailDTO;
import com.condigence.dto.UserDTO;
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
public class Cart implements Serializable {

	@Id
	private String id;
	private String userId;
	private String convId;
	private String lastUpdated;

	private float subtotalAmount;
	private float grandTotal;
	private int totalItemCount;
	private float discountAmount;
	private float taxAmount;

	private List<CartDetail> itemDetails;
	private List<Subscription> subscriptionDetails;

}
