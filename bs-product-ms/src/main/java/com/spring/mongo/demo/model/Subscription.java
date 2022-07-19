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
public class Subscription implements Serializable {

	@Id
	private String id;
	private String fromDate;
	private String toDate;
	private Integer noOfDays;
	private String frequency;
	private String userid;
	private String productId;
}
