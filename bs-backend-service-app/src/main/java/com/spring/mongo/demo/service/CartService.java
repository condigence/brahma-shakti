package com.spring.mongo.demo.service;


import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.exception.NotEnoughProductsInStockException;
import com.spring.mongo.demo.model.Cart;
import com.spring.mongo.demo.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

	void addToCart(CartBean cartBean);

	CartDTO getCartByUserId(String userId);

	void deleteCartByUserId(String userId);

	void addProduct(Product product);

	void removeProduct(Product product);

	Map<Product, Integer> getProductsInCart();

	void checkout() throws NotEnoughProductsInStockException;

	BigDecimal getTotal();
}
