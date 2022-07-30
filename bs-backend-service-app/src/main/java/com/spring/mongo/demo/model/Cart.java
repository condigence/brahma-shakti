package com.spring.mongo.demo.model;

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
	private List<CartDetail> itemDetails;
	private int totalItemCount;
	private float discountAmount;
	private float taxAmount;
	private float subtotalAmount;
	private float grandTotal;
	private String lastUpdated;
	
}
