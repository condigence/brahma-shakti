package com.spring.mongo.demo.service;


import com.spring.mongo.demo.bean.CartBean;
import com.spring.mongo.demo.bean.OrderBean;
import com.spring.mongo.demo.bean.ProductBean;
import com.spring.mongo.demo.dto.CartDTO;
import com.spring.mongo.demo.dto.OrderDTO;
import com.spring.mongo.demo.model.Order;

public interface OrderService {

	void placeOrder(OrderBean orderBean);

	OrderDTO getOrderByUserId(String userId);

	void deleteOrderByUserId(String userId);

	public Order saveOrder(final String razorpayOrderId, final String userId);

	public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret);




}
