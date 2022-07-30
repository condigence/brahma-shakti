package com.spring.mongo.demo.repository;



import com.spring.mongo.demo.model.Category;


import java.util.List;

public interface CategoryQueryDao {

    List<Category> getAll();


    Category getSingleCategoryByCategoryName(String CategoryName);

    List<Category> getCategoryByCategoryNameLike(String CategoryName);

}
