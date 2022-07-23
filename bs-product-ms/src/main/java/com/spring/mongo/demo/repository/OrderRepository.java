package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByUserId(String userId);

    Order save(Order order);

    List<Order> findAll();
}
