package com.condigence.repository.impl;

import com.condigence.model.Cart;
import com.condigence.repository.CartQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
class CartQueryDaoImpl implements CartQueryDao {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Cart getSingleCartByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        Cart cart = mongoTemplate.findOne(query, Cart.class);
        return cart;
    }

}
