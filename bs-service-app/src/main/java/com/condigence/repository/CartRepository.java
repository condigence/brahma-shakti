package com.condigence.repository;

import com.condigence.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, String> {

    Cart findByUserId(String userId);

    Cart save(Cart cart);

    List<Cart> findAll();
}
