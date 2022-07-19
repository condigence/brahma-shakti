package com.spring.mongo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

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
	private int price;
	private int discount;
	private float actualPrice;
	private String promoCode;
	private String category;
	private float rating;
	private boolean isSubscribable;
}
