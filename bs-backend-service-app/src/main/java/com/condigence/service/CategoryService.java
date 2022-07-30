package com.spring.mongo.demo.service;

import com.spring.mongo.demo.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

//    List<Category> getCategoryByCategoryName(String categoryName);

    Category getOneCategoryByCategoryName(String categoryName);

    List<Category> getCategoryByCategoryNameLike(String categoryName);


}
