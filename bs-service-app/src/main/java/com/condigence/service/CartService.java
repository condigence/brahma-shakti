package com.condigence.service;


import com.condigence.dto.CartDTO;
import com.condigence.model.Product;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Subscription;

public interface CartService {

	void addProduct(Product product);

	void removeProduct(Product product);

	CartDTO getProductsInCart();

	CartDTO getProductsInCart(String userId);

	void checkout() throws NotEnoughProductsInStockException;

	void unsubscribeProduct(Subscription subscription);

	CartDTO getProductsInCartByUserId(String userId);

	void subscribeProduct(Subscription subscription);

}
