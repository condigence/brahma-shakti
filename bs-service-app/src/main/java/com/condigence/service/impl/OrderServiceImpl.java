package com.condigence.service.impl;

import com.condigence.bean.OrderBean;
import com.condigence.dto.OrderDTO;
import com.condigence.model.Order;
import com.condigence.repository.OrderRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.service.OrderService;
import com.condigence.utils.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void placeOrder(OrderBean orderBean){
		Order order = new Order();
		order.setUserId(orderBean.getUserId());
		order.setCartId(orderBean.getCartId());
		order.setAddressId(orderBean.getAddressId());
		order.setPaymentMethod(orderBean.getPaymentMethod());
		repository.save(order);
	}

	@Override
	public OrderDTO getOrderByUserId(String userId) {
		Order order = repository.findByUserId(userId);
		OrderDTO orderDTO = new OrderDTO();


		// populate User Info
		orderDTO = populateUser(order, orderDTO);

		// Populate Cart Info
		orderDTO = populateCartInfo(order, orderDTO);

		// Populate Payment Info

		orderDTO = populatePaymentInfo(order, orderDTO);

		// Populate Order info

		order.setNumber(order.getNumber());
		order.setDateTime(order.getDateTime());
		order.setType(order.getType());
		order.setStatus(order.getStatus());
		order.setRazorpayOrderId(order.getRazorpayOrderId());
		order.setRazorpaySignature(order.getRazorpaySignature());





		return orderDTO;
	}

	private OrderDTO populatePaymentInfo(Order order, OrderDTO orderDTO) {

		return orderDTO;
	}

	private OrderDTO populateCartInfo(Order order, OrderDTO orderDTO) {

		order.setCartId(order.getCartId());
		// Populate Subscription Info


		// Populate products purchased Info
		return orderDTO;
	}

	private OrderDTO populateUser(Order order, OrderDTO orderDTO) {

		order.setUserId(order.getUserId());
		// populate Address Info



		// Populate Profile Info


		// populate User info

		// return dto
		return orderDTO;
	}

	/**
	 * @param userId
	 */
	@Override
	public void deleteOrderByUserId(String userId) {
		repository.delete(repository.findByUserId(userId));
	}

	@Transactional
	@Override
	public Order saveOrder(final String razorpayOrderId, final String userId) {
		Order order = new Order();
		order.setRazorpayOrderId(razorpayOrderId);
		order.setUserId(userId);
		return repository.save(order);
	}

	@Transactional
	@Override
	public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
		String errorMsg = null;
		try {
			Order order = repository.findByRazorpayOrderId(razorpayOrderId);
			// Verify if the razorpay signature matches the generated one to
			// confirm the authenticity of the details returned
			String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
			if (generatedSignature.equals(razorpaySignature)) {
				order.setRazorpayOrderId(razorpayOrderId);
				order.setRazorpayPaymentId(razorpayPaymentId);
				order.setRazorpaySignature(razorpaySignature);
				repository.save(order);
			} else {
				errorMsg = "Payment validation failed: Signature doesn't match";
			}
		} catch (Exception e) {
			logger.error("Payment validation failed", e);
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}
}
