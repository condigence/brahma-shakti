package com.condigence.repository;

import com.condigence.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);

    Product findOneById(String id);

    List<Product> findByNameLike(String name);




}
