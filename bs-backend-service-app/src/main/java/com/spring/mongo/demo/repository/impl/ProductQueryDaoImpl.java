package com.spring.mongo.demo.repository.impl;

import com.spring.mongo.demo.model.Employee;
import com.spring.mongo.demo.model.Product;
import com.spring.mongo.demo.repository.ProductQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
class ProductQueryDaoImpl implements ProductQueryDao {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Product> getAll() {
        System.out.println("Inside Product Query DAO Impl's get()");
        return mongoTemplate.findAll(Product.class);
    }


    @Override
    public List<Product> getProductByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return new ArrayList<>(mongoTemplate.find(query, Product.class));
    }


    @Override
    public Product getSingleProductByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Product.class);
    }


    @Override
    public List<Product> getProductByNameLike(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name, "i"));
        return mongoTemplate.find(query, Product.class);
    }
}
