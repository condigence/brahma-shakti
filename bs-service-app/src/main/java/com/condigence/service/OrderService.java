package com.condigence.service;


import com.condigence.bean.OrderBean;
import com.condigence.dto.OrderDTO;
import com.condigence.model.Order;

public interface OrderService {

	void placeOrder(OrderBean orderBean);

	OrderDTO getOrderByUserId(String userId);

	void deleteOrderByUserId(String userId);

	public Order saveOrder(final String razorpayOrderId, final String userId);

	public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret);




}
