package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByName(String name);

    Product findOneById(String id);

    List<Product> findByNameLike(String name);




}
