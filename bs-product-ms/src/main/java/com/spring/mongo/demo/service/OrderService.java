package com.spring.mongo.demo.service;


import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.bean.OrderBean;
import com.spring.mongo.demo.bean.ProductBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.OrderDTO;

public interface OrderService {

	void placeOrder(OrderBean orderBean);

	OrderDTO getOrderByUserId(String userId);

	void deleteOrderByUserId(String userId);


}
