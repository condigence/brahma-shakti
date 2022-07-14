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
public class Order implements Serializable {

	@Id
	private String id;
	private String userId;
	private String type;
	private String dateTime;
	private String status;
	private List<OrderDetail> orderItems;
	
}
