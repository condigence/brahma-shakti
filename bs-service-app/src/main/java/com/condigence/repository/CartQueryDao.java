package com.condigence.repository;

import com.condigence.model.Cart;

public interface CartQueryDao {


    Cart getSingleCartByUserId(String userId);

}
