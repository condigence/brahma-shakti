package com.spring.mongo.demo.repository;

import com.spring.mongo.demo.model.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String>

{

    Category findByCategoryName(String categoryName);

    List<Category> findByCategoryNameLike(String categoryName);


}
