package com.spring.mongo.demo.repository.impl;

import com.spring.mongo.demo.model.Category;

import com.spring.mongo.demo.repository.CategoryQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
class CategoryQueryDaoImpl implements CategoryQueryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Category> getAll() {
        System.out.println("Inside Category Query DAO Impl's get()");
        return mongoTemplate.findAll(Category.class);
    }


    @Override
    public Category getSingleCategoryByCategoryName(String name) {

        Query query = new Query();
        query.addCriteria(Criteria.where("categoryName").is(name));

        return mongoTemplate.findOne(query, Category.class);
    }


    // "firstName"  --> database property (not related to java code)
    // select * from employee where lastName like %Gurung% limit 1 --> (case insensitive)
    @Override
    public List<Category> getCategoryByCategoryNameLike(String categoryName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("categoryName").regex(categoryName, "i"));

        return mongoTemplate.find(query, Category.class);
    }


}
