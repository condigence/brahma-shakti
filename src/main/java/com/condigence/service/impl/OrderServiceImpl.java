package com.condigence.service.impl;

import com.condigence.bean.OrderBean;
import com.condigence.dto.CartDTO;
import com.condigence.dto.OrderDTO;
import com.condigence.model.Cart;
import com.condigence.model.Order;
import com.condigence.repository.OrderRepository;
import com.condigence.service.CartService;
import com.condigence.service.OrderService;
import com.condigence.service.UserService;
import com.condigence.utils.HelperUtil;
import com.condigence.utils.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;


    @Autowired
    private CartService cartService;


    @Transactional
    @Override
    public void placeOrder(OrderBean orderBean) {
        Order order = new Order();
        order.setUserId(orderBean.getUserId()); // get from cart if userId is null
        order.setCartId(orderBean.getCartId()); // get from cart - user - profile - address -  if address Id  is null
        order.setAddressId(orderBean.getAddressId());
        order.setPaymentMethod(orderBean.getPaymentMethod());
        order.setStatus("CONFIRMED");
        order.setNumber(UUID.randomUUID().toString());
        order.setDateTime(HelperUtil.getCurrentDateTIme());
        repository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        return repository.findById(id).get();
    }

//	@Override
//	public OrderDTO getOrderByUserId(String userId) {
//		Order order = repository.findByUserId(userId);
//		OrderDTO orderDTO = new OrderDTO();
//		UserDTO userDTO = userService.getUserById(order.getUserId());
//		orderDTO.setUser(userDTO);
//
//		// Populate Cart Info
//		orderDTO = populateCartInfo(order, orderDTO);
//
//		// Populate Payment Info
//
//		orderDTO = populatePaymentInfo(order, orderDTO);
//
//		// Populate Order info
//
//		order.setNumber(order.getNumber());
//		order.setDateTime(order.getDateTime());
//		order.setType(order.getType());
//		order.setStatus(order.getStatus());
//		order.setRazorpayOrderId(order.getRazorpayOrderId());
//		order.setRazorpaySignature(order.getRazorpaySignature());
//
//		return orderDTO;
//	}

    @Override
    public List<OrderDTO> getAllOrderByUserId(String userId) {
        List<Order> myOrders = repository.findAllByUserId(userId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        // Populate orders here
        //TODO: 06-12-2022
        for (Order order : myOrders) {
            OrderDTO dto = new OrderDTO();
            dto.setNumber(order.getNumber());
            dto.setDateTime(order.getDateTime());
            dto.setType(order.getType());
            dto.setStatus(order.getStatus());
            dto.setRazorpayOrderId(order.getRazorpayOrderId());
            dto.setRazorpaySignature(order.getRazorpaySignature());
            dto.setUser(userService.getUserById(order.getUserId()));
            //populateCartInfo
            Cart cart = cartService.getCartById(order.getCartId());
            CartDTO cartDTO = new CartDTO();
            cartDTO = cartService.populateCartDetails(cart, cartDTO);
            dto.setCartDTO(cartDTO);
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setGrandTotal(cart.getGrandTotal());
            dto.setTotalItemCount(cart.getTotalItemCount());

            orderDTOS.add(dto);
        }
        return orderDTOS;
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

    @Override
    public void deleteById(String orderId) {
        repository.deleteById(orderId);
    }
}
