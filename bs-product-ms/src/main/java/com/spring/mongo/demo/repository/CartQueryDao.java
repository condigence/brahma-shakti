package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Cart;
import java.util.Optional;

public interface CartQueryDao {


	Cart getSingleCartByUserId(String userId);

}
