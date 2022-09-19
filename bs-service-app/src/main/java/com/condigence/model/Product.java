package com.condigence.model;

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
public class Product implements Serializable {

	@Id
	private String id;
	private String name;
	private int quantityInStock;
	private String imageLink;
	private String description;
	private int displayPrice;
	private int price;
	private String unit;
	private String category;
	private String type;
	private String offers;
	private int discount;
	private double rating;
	private boolean isSubscribable;
	private int quantity;

	
}
