package com.condigence.repository;

import com.condigence.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

    Cart findByUserId(String userId);

    Cart findByConvId(String convId);

    Cart save(Cart cart);

}
