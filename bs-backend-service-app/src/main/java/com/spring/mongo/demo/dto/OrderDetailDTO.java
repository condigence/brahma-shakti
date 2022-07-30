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
public class OrderDetailDTO implements Serializable {

	@Id
	private String id;
	private String productId;
	private Integer quantity;
	private Integer orderDiscount;

	
}
