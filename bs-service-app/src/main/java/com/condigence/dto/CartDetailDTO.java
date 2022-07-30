package com.condigence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailDTO implements Serializable {

	@Id
	private String id;
	private String title;
	private int itemQuantity;
	private int itemCount;
	private String unit;
	private BigDecimal price;
	private int totalAmount;
	private int discount;
	
}
