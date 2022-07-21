package com.spring.mongo.demo.repository.impl;

import com.spring.mongo.demo.model.Employee;
import com.spring.mongo.demo.model.User;
import com.spring.mongo.demo.repository.UserQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserQueryDaoImpl implements UserQueryDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> getAll() {
        System.out.println("Inside Employee Query DAO Impl's get()");
        return mongoTemplate.findAll(User.class);
    }


    @Override
    public List<User> getUserByFirstName(String firstName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(firstName));
        return new ArrayList<>(mongoTemplate.find(query, User.class));
    }


    @Override
    public User getSingleUserByFirstName(String name) {

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is(name));

        return mongoTemplate.findOne(query, User.class);
    }


    // "firstName"  --> database property (not related to java code)
    // select * from employee where lastName like %Gurung% limit 1 --> (case insensitive)
    @Override
    public List<User> getUserByFirstNameLike(String firstName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").regex(firstName, "i"));

        return mongoTemplate.find(query, User.class);
    }


    @Override
    public User getSingleUserByLastName(String lastName) {

        Query query = new Query();
        query.addCriteria(Criteria.where("lastName").regex(lastName, "i"));

        return mongoTemplate.findOne(query, User.class);
    }
    @Override
    public List<User> getUserByEmail(float email) {

        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return new ArrayList<>(mongoTemplate.find(query, User.class));
    }

    @Override
    public List<User> getUserByAddress(float address) {

        Query query = new Query();
        query.addCriteria(Criteria.where("address").is(address));
        return new ArrayList<>(mongoTemplate.find(query, User.class));
    }

    @Override
    public List<User> getUserByContact(float contact) {

        Query query = new Query();
        query.addCriteria(Criteria.where("contact").is(contact));
        return new ArrayList<>(mongoTemplate.find(query, User.class));
    }

}
