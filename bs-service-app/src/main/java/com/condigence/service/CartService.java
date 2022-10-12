package com.condigence.service;


import com.condigence.dto.CartDTO;
import com.condigence.model.Product;
import com.condigence.exception.NotEnoughProductsInStockException;

public interface CartService {

//	void addToCart(CartBean cartBean);
//
//	CartDTO getCartByUserId(String userId);
//
//	void deleteCartByUserId(String userId);

	void addProduct(Product product);

	void removeProduct(Product product);

	//Map<Product, Integer> getProductsInCart();

	CartDTO getProductsInCart();

	void checkout() throws NotEnoughProductsInStockException;

	void subscribeProduct(Product product);

	void unSubscribeProduct(Product product);

//	Integer getTotal();
}
