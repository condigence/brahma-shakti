package com.condigence.dto;

import com.condigence.bean.ProductBean;
import com.condigence.bean.SubscriptionBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private Map<String, ProductBean> productsPicked;
	private Map<String, SubscriptionBean> subscriptions;

	private String userId;
	private String convId;
	private UserDTO userDTO;

	String outOfStockMessage;
	
}
