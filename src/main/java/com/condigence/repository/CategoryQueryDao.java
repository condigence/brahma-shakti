package com.condigence.repository;


import com.condigence.model.Category;

import java.util.List;

public interface CategoryQueryDao {

    List<Category> getAll();


    Category getSingleCategoryByCategoryName(String CategoryName);

    List<Category> getCategoryByCategoryNameLike(String CategoryName);

}
