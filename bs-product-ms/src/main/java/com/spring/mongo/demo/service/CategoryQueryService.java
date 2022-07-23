package com.spring.mongo.demo.service;
import com.spring.mongo.demo.model.Category;
import java.util.List;

public interface CategoryQueryService {

    List<Category> getAll();



    List<Category> getCategoryByCategoryNameLike(String categoryName);

    Category getOneCategoryByCategoryName(String categoryName);


}
