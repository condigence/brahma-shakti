package com.condigence.model;

import com.condigence.dto.ProductDTO;
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
public class CartDetail implements Serializable {

	@Id
	private String id;
	private int itemQuantity;
	private int totalAmount;
	private String productId;
	
}
