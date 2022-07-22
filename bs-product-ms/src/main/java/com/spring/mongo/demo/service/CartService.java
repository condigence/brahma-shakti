package com.spring.mongo.demo.service;


import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Cart;
import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface CartService {

	void addToCart(CartBean cartBean);

	CartDTO getCartByUserId(String userId);

	void deleteCartByUserId(String userId);
}
