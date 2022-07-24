package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Category;
import com.spring.mongo.demo.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StockRepository extends MongoRepository<Stock, String> {

    Stock findByProductId(String productId);

}
