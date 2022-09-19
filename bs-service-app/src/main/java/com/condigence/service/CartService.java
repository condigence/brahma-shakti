package com.condigence.service;


import com.condigence.bean.CartBean;
import com.condigence.dto.CartDTO;
import com.condigence.model.Product;
import com.condigence.exception.NotEnoughProductsInStockException;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

	void addToCart(CartBean cartBean);

	CartDTO getCartByUserId(String userId);

	void deleteCartByUserId(String userId);

	void addProduct(Product product);

	void removeProduct(Product product);

	Map<Product, Integer> getProductsInCart();

	void checkout() throws NotEnoughProductsInStockException;

	Integer getTotal();
}
