package com.condigence.service;


import com.condigence.bean.OrderBean;
import com.condigence.dto.OrderDTO;
import com.condigence.model.Order;

import java.util.List;

public interface OrderService {

	void placeOrder(OrderBean orderBean);

	Order getOrderById(String id);

	List<OrderDTO> getAllOrderByUserId(String userId);

	void deleteOrderByUserId(String userId);

	public Order saveOrder(final String razorpayOrderId, final String userId);

	public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret);


	void deleteById(String orderId);
}
