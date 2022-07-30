package com.condigence.service;

import com.condigence.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

//    List<Category> getCategoryByCategoryName(String categoryName);

    Category getOneCategoryByCategoryName(String categoryName);

    List<Category> getCategoryByCategoryNameLike(String categoryName);


}
