package com.spring.mongo.demo.model;

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
public class Product implements Serializable {

	@Id
	private String id;
	private String name;
	private int quantityInStock;
	private String imageLink;
	private String description;
	private float displayPrice;
	private float actualPrice;
	private String offer;
	private boolean isSubscribed;

	
}
