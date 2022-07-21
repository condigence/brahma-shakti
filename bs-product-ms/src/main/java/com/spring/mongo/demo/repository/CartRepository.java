package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Cart;
import com.spring.mongo.demo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Cart findByUserId(String userId);

    List<Cart> findAll();
}
