package com.condigence.service.impl;

import com.condigence.bean.OrderBean;
import com.condigence.bean.OrderDetailBean;
import com.condigence.dto.OrderDTO;
import com.condigence.dto.OrderDetailDTO;
import com.condigence.model.Order;
import com.condigence.model.OrderDetail;
import com.condigence.model.Product;
import com.condigence.repository.OrderRepository;
import com.condigence.repository.ProductRepository;
import com.condigence.service.OrderService;
import com.condigence.utils.Signature;
import com.spring.mongo.demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
		/////
		order.setUserId(orderBean.getUserId());
		order.setDiscountAmount(orderBean.getDiscountAmount());
		order.setGrandTotal(orderBean.getGrandTotal());
		order.setLastUpdated(orderBean.getLastUpdated());
		order.setSubtotalAmount(orderBean.getSubtotalAmount());
		order.setTaxAmount(orderBean.getTaxAmount());
		order.setDateTime(orderBean.getDateTime());
		order.setGst(orderBean.getGst());
		order.setNumber(orderBean.getNumber());
		order.setServiceCharge(orderBean.getServiceCharge());
		order.setTotalItemCount(orderBean.getTotalItemCount());
		order.setType(orderBean.getType());
		order.setStatus(orderBean.getStatus());


		List<OrderDetail> orderDetails = new ArrayList<>();
		for(OrderDetailBean items :orderBean.getOrderItems()) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = productRepository.findOneById(items.getProductId());
			orderDetail.setOrderDiscount(product.getDiscount());
			orderDetail.setId(items.getId());
			orderDetail.setQuantity(items.getQuantity());
			orderDetail.setProductId(product.getId());
			orderDetails.add(orderDetail);
		}

		order.setOrderItems(orderDetails);
		//////////
		repository.save(order);

	}

	@Override
	public OrderDTO getOrderByUserId(String userId) {
		Order order = repository.findByUserId(userId);
		if(order != null){
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setUserId(order.getUserId());
			orderDTO.setDiscountAmount(order.getDiscountAmount());
			orderDTO.setGrandTotal(order.getGrandTotal());
			orderDTO.setLastUpdated(order.getLastUpdated());
			orderDTO.setSubtotalAmount(order.getSubtotalAmount());
			orderDTO.setTaxAmount(order.getTaxAmount());
			orderDTO.setDateTime(order.getDateTime());
			orderDTO.setGst(order.getGst());
			orderDTO.setNumber(order.getNumber());
			orderDTO.setServiceCharge(order.getServiceCharge());
			orderDTO.setTotalItemCount(order.getTotalItemCount());
			orderDTO.setType(order.getType());
			orderDTO.setStatus(order.getStatus());

			List<OrderDetailDTO> orderDetails = new ArrayList<>();
			for(OrderDetail items :order.getOrderItems()) {
				OrderDetailDTO orderDetail = new OrderDetailDTO();
				Product product = productRepository.findOneById(items.getProductId());
				orderDetail.setOrderDiscount(product.getDiscount());
				orderDetail.setId(items.getId());
				orderDetail.setQuantity(items.getQuantity());
				orderDetail.setProductId(product.getId());
				orderDetails.add(orderDetail);
			}
			orderDTO.setOrderItems(orderDetails);
			return orderDTO;
		}else{
			return OrderDTO.builder().id("0").userId("Not Found").build();
		}

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
