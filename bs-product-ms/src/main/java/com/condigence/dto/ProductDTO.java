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
public class ProductDTO implements Serializable {

	private String id;
	private String title;
	private int stockLeft;
	private String image;
	private String unit;
	private String description;
	private BigDecimal price;
	private int discount;
	private String[] promoCodes;
	private String category;
	private double rating;
	private boolean isSubscribable;
}
