package com.condigence.service;


import com.condigence.dto.CartDTO;
import com.condigence.model.Product;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Subscription;

public interface CartService {

	void addProduct(Product product, String convId, String userId);

	CartDTO getProductsInCart(String convId, String userId);

	void removeProduct(Product product, String convId, String userId);

	void removeAllProduct(Product product, String convId, String userId);

	void checkout(CartDTO cartDTO) throws NotEnoughProductsInStockException;

	void unsubscribeProduct(Subscription subscription);

	void unsubscribeAllProduct(Subscription subscription);

	void subscribeProduct(Subscription subscription);

	void updateSubscription(Subscription subscription);


}
