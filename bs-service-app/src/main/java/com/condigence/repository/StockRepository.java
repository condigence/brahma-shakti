package com.condigence.repository;

import com.condigence.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, String> {

    Stock findByProductId(String productId);

}
