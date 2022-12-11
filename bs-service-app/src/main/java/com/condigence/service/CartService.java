package com.condigence.service;


import com.condigence.dto.CartDTO;
import com.condigence.model.Product;
import com.condigence.exception.NotEnoughProductsInStockException;
import com.condigence.model.Subscription;

public interface CartService {

	void addProduct(Product product, String convId, String userId);

	CartDTO getCartByUserId(String userId);

	CartDTO getProductsInCart(String convId, String userId);

	void removeProduct(Product product, String convId, String userId);

	void removeAllProduct(Product product, String convId, String userId);

	CartDTO checkout(CartDTO cartDTO) throws NotEnoughProductsInStockException;

	void unsubscribeProduct(Subscription subscription, String convId, String userId);

	void unsubscribeAllProduct(Subscription subscription, String convId, String userId);

	void subscribeProduct(Subscription subscription, String convId, String userId);

	void updateSubscription(Subscription subscription, String convId, String userId);

    void clearCart(String convId, String userId);
}
