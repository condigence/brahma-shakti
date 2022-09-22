package com.condigence.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO implements Serializable {

	private String id;
	private String title;
	private int stockLeft;
	private String image;
	private String unit;
	private String description;
	private int price;
	private int discount;
	private String promoCodes;
	private String category;
	private String productType;
	private double rating;
	private boolean isSubscribable;

}
