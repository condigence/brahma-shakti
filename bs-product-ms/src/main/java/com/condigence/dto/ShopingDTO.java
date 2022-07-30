package com.condigence.dto;

import com.condigence.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopingDTO implements Serializable {

	Map<Product, Integer> productsInCart;
	String total;
	String outOfStockMessage;
	
}
