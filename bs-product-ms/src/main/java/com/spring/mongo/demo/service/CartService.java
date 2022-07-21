package com.spring.mongo.demo.service;


import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.ProductDTO;
import com.spring.mongo.demo.model.Cart;
import com.spring.mongo.demo.model.Product;

import java.util.List;

public interface CartService {

	CartDTO getCartByUserId(String userId);
}
