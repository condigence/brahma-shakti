package com.condigence.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBean implements Serializable {

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
	private String type;
	private double rating;
	private boolean isSubscribable;
}
