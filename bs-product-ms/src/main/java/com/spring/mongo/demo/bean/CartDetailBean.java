package com.spring.mongo.demo.bean;

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
public class CartDetailBean implements Serializable {


	private String productId;
	private int itemCount;
	private int totalAmount;
	
}
