package com.condigence.repository;

import com.condigence.model.Category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String>

{

    Category findByCategoryName(String categoryName);

    List<Category> findByCategoryNameLike(String categoryName);


}
