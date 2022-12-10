package com.condigence.repository;

import com.condigence.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByUserId(String userId);

    Order save(Order order);

    Order findByRazorpayOrderId(String orderId);

    List<Order> findAllByUserId(String userId);
}
